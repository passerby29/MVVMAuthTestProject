package com.example.mvvmauthtestproject.ui.register

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmauthtestproject.data.RegisterEntity
import com.example.mvvmauthtestproject.data.RegisterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


private const val TAG = "MY_TAG"

class RegisterViewModel(private val repository: RegisterRepository, application: Application) :
    AndroidViewModel(application), Observable {

    init {
        Log.d(TAG, "init")
    }

    private var userData: String? = null

    var userDetailsLiveData = MutableLiveData<Array<RegisterEntity>>()

    @Bindable
    val inputFirstName = MutableLiveData<String?>()

    @Bindable
    val inputLastName = MutableLiveData<String?>()

    @Bindable
    val inputUsername = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateTo = MutableLiveData<Boolean>()

    val navigateTo: LiveData<Boolean>
        get() = _navigateTo

    private val _errorToast = MutableLiveData<Boolean>()

    val errorToast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errorToastUsername: LiveData<Boolean>
        get() = _errorToastUsername

    fun submitButton() {
        Log.i(TAG, "Inside SUBMIT BUTTON")
        if (inputFirstName.value == null || inputLastName.value == null ||
            inputUsername.value == null || inputPassword.value == null
        ) {
            _errorToast.value = true
        } else {
            uiScope.launch {
                val userNames = repository.getUserName(inputUsername.value!!)
                Log.i(TAG, userNames.toString() + "-------------------")
                if (userNames != null) {
                    _errorToastUsername.value = true
                    Log.i(TAG, "Inside is Not null")
                } else {
                    Log.i(TAG, userDetailsLiveData.value.toString())
                    Log.i(TAG, "OK im in")
                    val firstName = inputFirstName.value!!
                    val lastName = inputLastName.value!!
                    val userName = inputUsername.value!!
                    val password = inputPassword.value!!
                    Log.i(TAG, "Inside Submit")
                    insert(RegisterEntity(0, firstName, lastName, userName, password))
                    inputFirstName.value = null
                    inputLastName.value = null
                    inputFirstName.value = null
                    inputFirstName.value = null
                    _navigateTo.value = true
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun doneNavigating() {
        _navigateTo.value = false
        Log.i(TAG, "doneNavigating")
    }

    fun doneToast() {
        _errorToast.value = false
        Log.i(TAG, "doneToast")
    }

    fun doneToastUsername() {
        _errorToastUsername.value = false
        Log.i(TAG, "doneToastUsername")
    }

    private fun insert(user: RegisterEntity): Job =
        viewModelScope.launch { repository.insert(user) }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}