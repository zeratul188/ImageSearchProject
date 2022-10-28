package com.example.imagesearchproject

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesearchproject.add.AddActivity

class MainViewModel: ViewModel() {
    /*val data: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        data.value = "helloworld"
    }*/

    fun onAddClick() {
        val intent = Intent(App.context(), AddActivity::class.java)
        App.context().startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK))
    }
}