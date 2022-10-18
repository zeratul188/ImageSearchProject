package com.example.imagesearchproject.add

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesearchproject.CustomToast
import com.example.imagesearchproject.room.ImageDatabase
import com.example.imagesearchproject.room.ImageItem

class AddViewModel(
    private val activity: AddActivity,
    private val context: Context
): ViewModel() {
    val item: MutableLiveData<ImageItem> by lazy {
        MutableLiveData<ImageItem>().also {
            item.value = initItem()
        }
    }

    fun initItem(): ImageItem {
        return ImageItem(0, "", "", "")
    }

    fun onAddClick() {
        val db = ImageDatabase.getInstance(context)
        item.value?.let { item ->
            db?.imageDao()?.insertAll(item)
            val toast = CustomToast(context)
            toast.createToast("아이템을 추가하였습니다.", false)
            toast.show()
        }
        activity.finish()
    }
}