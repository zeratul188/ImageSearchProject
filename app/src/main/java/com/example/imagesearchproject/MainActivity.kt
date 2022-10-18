package com.example.imagesearchproject

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchproject.databinding.ActivityMainBinding
import com.example.imagesearchproject.room.ImageDatabase
import com.example.imagesearchproject.room.ImageItem

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private lateinit var items: ArrayList<ImageItem>
    private lateinit var imageAdapter: ImageRecyclerAdapter

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainViewModel = viewModel

        val runable = Runnable {
            try {
                val db = ImageDatabase.getInstance(this)
                val dao = db?.imageDao()
                items = (dao?.getAll() as ArrayList<ImageItem>?)!!
                with(binding) {
                    imageAdapter = ImageRecyclerAdapter(items)
                    listView.adapter = imageAdapter
                    val spaceDecoration = VerticalSpaceItemDecoration(20)
                    listView.addItemDecoration(spaceDecoration)
                }
            } catch (e: Exception) {
                Log.d("error", "Error - $e")
            }
        }

        val thread = Thread(runable)
        thread.start()

        /*val dataObserver = Observer<String> { data ->
            binding.edtSearch.hint = data
        }
        viewModel.data.observe(this, dataObserver)*/

        /*binding.fabAdd.setOnClickListener {
            viewModel.data.value = binding.edtContent.text.toString()
        }*/


    }

    override fun onResume() {
        super.onResume()
        val runable = Runnable {
            try {
                items.clear()
                val db = ImageDatabase.getInstance(this)
                val dao = db?.imageDao()
                val list = dao?.getAll()!!
                items.addAll(list)
                handler.post(Runnable {
                    imageAdapter.notifyDataSetChanged()
                })
            } catch (e: Exception) {
                Log.d("error", "Error - $e")
            }
        }

        val thread = Thread(runable)
        thread.start()
    }

    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = verticalSpaceHeight
        }
    }
}