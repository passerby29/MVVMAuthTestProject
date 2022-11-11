package com.example.mvvmauthtestproject.ui.userDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmauthtestproject.R
import com.example.mvvmauthtestproject.data.RegisterDatabase
import com.example.mvvmauthtestproject.data.RegisterRepository
import com.example.mvvmauthtestproject.databinding.FragmentUserDetailsBinding

class UserDetailsFragment : Fragment() {
    private lateinit var userDetailsViewModel: UserDetailsViewModel
    private lateinit var binding: FragmentUserDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_details, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = UserDetailsViewModelFactory(repository, application)

        userDetailsViewModel = ViewModelProvider(this, factory)[UserDetailsViewModel::class.java]

        binding.userDetailsLayout = userDetailsViewModel

        binding.lifecycleOwner = this

        userDetailsViewModel.navigateTo.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished == true) {
                val action =
                    UserDetailsFragmentDirections.actionUserDetailsFragmentToLoginFragment()
                NavHostFragment.findNavController(this).navigate(action)
                userDetailsViewModel.doneNavigating()
            }
        }

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(this.context)
        displayUsersList()
    }

    private fun displayUsersList() {
        Log.i("TAG", "displayUsersList")
        userDetailsViewModel.users.observe(viewLifecycleOwner) {
            binding.usersRecyclerView.adapter = MyRecyclerViewAdapter(it)
        }
    }
}