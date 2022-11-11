package com.example.mvvmauthtestproject.ui.userDetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmauthtestproject.data.RegisterRepository
import com.example.mvvmauthtestproject.ui.login.LoginViewModel

class UserDetailsViewModelFactory(
    private val repository: RegisterRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
            return UserDetailsViewModel(repository, application) as T
        }
        throw IllegalAccessException("Unknown ViewModel Class")
    }
}