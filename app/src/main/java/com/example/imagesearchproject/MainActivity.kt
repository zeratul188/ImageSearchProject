package com.example.imagesearchproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.imagesearchproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        /*val dataObserver = Observer<String> { data ->
            binding.txtContent.text = data
        }
        viewModel.data.observe(this, dataObserver)

        binding.btnInsert.setOnClickListener {
            viewModel.data.value = binding.edtContent.text.toString()
        }*/


    }
}