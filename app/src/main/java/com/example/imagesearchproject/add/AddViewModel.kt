package com.example.imagesearchproject.add

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesearchproject.App
import com.example.imagesearchproject.CustomToast
import com.example.imagesearchproject.room.ImageDatabase
import com.example.imagesearchproject.room.ImageItem

class AddViewModel(
    private val activity: AddActivity
): ViewModel() {
    val item: MutableLiveData<ImageItem> by lazy {
        MutableLiveData<ImageItem>()
    }

    fun onAddClick() {
        val db = ImageDatabase.getInstance(App.context())
        item.value?.let { item ->
            db?.imageDao()?.insertAll(item)
            val toast = CustomToast(App.context())
            toast.createToast("아이템을 추가하였습니다.", false)
            toast.show()
        }
        activity.finish()
    }
}