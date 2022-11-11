package com.example.mvvmauthtestproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.*
import com.example.mvvmauthtestproject.R
import com.example.mvvmauthtestproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED VARIABLE")
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

    }
}