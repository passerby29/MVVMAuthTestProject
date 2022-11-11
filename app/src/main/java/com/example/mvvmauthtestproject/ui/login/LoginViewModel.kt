package com.example.mvvmauthtestproject.ui.login

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmauthtestproject.data.RegisterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "MY_TAG"

class LoginViewModel(private val repository: RegisterRepository, application: Application) :
    AndroidViewModel(application), Observable {

    val users = repository.users

    @Bindable
    val inputUsername = MutableLiveData<String?>()

    @Bindable
    val inputPassword = MutableLiveData<String?>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToRegister = MutableLiveData<Boolean>()

    val navigateToRegister: LiveData<Boolean>
        get() = _navigateToRegister

    private val _navigateToUserDetails = MutableLiveData<Boolean>()

    val navigateToUserDetails: LiveData<Boolean>
        get() = _navigateToUserDetails

    private val _errorToast = MutableLiveData<Boolean>()

    val errorToast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errorUsername: LiveData<Boolean>
        get() = _errorToastUsername

    private val _errorInvalidPassword = MutableLiveData<Boolean>()

    val errorInvalidPassword: LiveData<Boolean>
        get() = _errorInvalidPassword

    fun signUp() {
        _navigateToRegister.value = true
    }

    fun loginButton() {
        if (inputUsername.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
                val userNames = repository.getUserName(inputUsername.value!!)
                if (userNames != null) {
                    if (userNames.password == inputPassword.value) {
                        inputUsername.value = null
                        inputPassword.value = null
                        _navigateToUserDetails.value = true
                    } else {
                        _errorInvalidPassword.value = true
                    }
                } else {
                    _errorToastUsername.value = true
                }
            }
        }
    }

    fun doneNavigatingRegister() {
        _navigateToRegister.value = false
    }

    fun doneNavigatingUserDetails() {
        _navigateToUserDetails.value = false
    }

    fun doneToast() {
        _errorToast.value = false
        Log.i(TAG, "doneToast")
    }

    fun doneToastErrorUsername() {
        _errorToastUsername.value = false
        Log.i(TAG, "doneToastErrorUsername")
    }

    fun doneToastInvalidPassword() {
        _errorInvalidPassword.value = false
        Log.i(TAG, "doneToastInvalidPassword")
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}