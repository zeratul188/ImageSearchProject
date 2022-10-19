package com.example.imagesearchproject.room

import android.util.Log
import androidx.databinding.ObservableField
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.imagesearchproject.App
import com.example.imagesearchproject.ImageRecyclerAdapter

@Entity
data class ImageItem(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "content") var content: String?,
    @ColumnInfo(name = "url") var url: String?,
    @ColumnInfo(name = "follow") var follow: Int?
)
