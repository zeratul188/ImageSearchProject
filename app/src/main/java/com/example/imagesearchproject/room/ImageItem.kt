package com.example.imagesearchproject.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageItem(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "content") var content: String?,
    @ColumnInfo(name = "url") var url: String?
)
