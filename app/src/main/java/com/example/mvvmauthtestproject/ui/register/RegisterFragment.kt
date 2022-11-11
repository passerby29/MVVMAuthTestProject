package com.example.mvvmauthtestproject.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.mvvmauthtestproject.R
import com.example.mvvmauthtestproject.data.RegisterDatabase
import com.example.mvvmauthtestproject.data.RegisterRepository
import com.example.mvvmauthtestproject.databinding.FragmentRegisterBinding

private const val TAG = "MYTAG"

class RegisterFragment : Fragment() {
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = RegisterViewModelFactory(repository, application)

        registerViewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        binding.myViewModel = registerViewModel

        binding.lifecycleOwner = this

        registerViewModel.navigateTo.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished == true) {
                Log.i(TAG, "onCreateView")
                displayUsersList()
                registerViewModel.doneNavigating()
            }
        }

        registerViewModel.userDetailsLiveData.observe(viewLifecycleOwner) {
            Log.i(TAG, "$it-------------")
        }

        registerViewModel.errorToast.observe(viewLifecycleOwner) { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        registerViewModel.errorToastUsername.observe(viewLifecycleOwner) { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Username already taken", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }

    private fun displayUsersList() {
        Log.i(TAG, "displayUsersList")
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
}