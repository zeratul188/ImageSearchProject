package com.example.imagesearchproject.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddViewModelFactory(private val activity: AddActivity) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            return AddViewModel(activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}