package com.example.imagesearchproject.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageDao {
    @Query("SELECT * FROM imageitem")
    fun getAll(): List<ImageItem>

    @Query("SELECT * FROM imageitem WHERE title LIKE :title")
    fun findByTitle(title: String): ImageItem

    @Insert
    fun insertAll(vararg imageItems: ImageItem)

    @Delete
    fun delete(imageItem: ImageItem)
}