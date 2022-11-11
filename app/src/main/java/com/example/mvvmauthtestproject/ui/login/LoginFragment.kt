package com.example.mvvmauthtestproject.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.mvvmauthtestproject.R
import com.example.mvvmauthtestproject.data.RegisterDatabase
import com.example.mvvmauthtestproject.data.RegisterRepository
import com.example.mvvmauthtestproject.databinding.FragmentLoginBinding

private const val TAG = "MY_TAG"

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = LoginViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        binding.myLoginViewModel = loginViewModel

        binding.lifecycleOwner = this

        loginViewModel.navigateToRegister.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                Log.i(TAG, "onCreateView")
                displayUsersList()
                loginViewModel.doneNavigatingRegister()
            }
        })


        loginViewModel.errorToast.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
                loginViewModel.doneToast()
            }
        })

        loginViewModel.errorUsername.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(
                    requireContext(), "User doesn't exist, please Register", Toast.LENGTH_SHORT
                ).show()
                loginViewModel.errorUsername
            }
        })

        loginViewModel.errorInvalidPassword.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(
                    requireContext(), "Please check your password", Toast.LENGTH_SHORT
                ).show()
                loginViewModel.errorInvalidPassword
            }
        })

        loginViewModel.navigateToUserDetails.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                Log.i(TAG, "navigating")
                navigateToUserDetails()
                loginViewModel.doneNavigatingUserDetails()
            }
        })

        return binding.root
    }

    private fun displayUsersList() {
        Log.i(TAG, "displayUsersList")
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun navigateToUserDetails() {
        Log.i(TAG, "navigateToUserDetails")
        val action = LoginFragmentDirections.actionLoginFragmentToUserDetailsFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
}