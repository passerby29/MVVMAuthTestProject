package com.example.mvvmauthtestproject.ui.userDetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmauthtestproject.data.RegisterRepository

private const val TAG = "MY_TAG"

class UserDetailsViewModel(
    private val repository: RegisterRepository,
    application: Application
) : AndroidViewModel(application) {

    val users = repository.users

    init {
        Log.i(TAG, "inside_user_list_init")
    }

    private val _navigateTo = MutableLiveData<Boolean>()

    val navigateTo: LiveData<Boolean>
        get() = _navigateTo

    fun doneNavigating() {
        _navigateTo.value = false
    }

    fun backButtonClick() {
        _navigateTo.value = true
    }
}