package com.example.imagesearchproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.imagesearchproject.databinding.ActivityMainBinding
import com.example.imagesearchproject.room.ImageDatabase
import com.example.imagesearchproject.room.ImageItem

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private val items = ArrayList<ImageItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainViewModel = viewModel
        val db = ImageDatabase.getInstance(this)

        with(binding) {
            val imageAdapter = ImageRecyclerAdapter(items)
            listView.adapter = imageAdapter
        }

        /*val dataObserver = Observer<String> { data ->
            binding.edtSearch.hint = data
        }
        viewModel.data.observe(this, dataObserver)*/

        /*binding.fabAdd.setOnClickListener {
            viewModel.data.value = binding.edtContent.text.toString()
        }*/


    }
}