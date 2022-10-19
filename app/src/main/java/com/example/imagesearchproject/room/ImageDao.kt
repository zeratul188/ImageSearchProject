package com.example.imagesearchproject.room

import androidx.room.*

@Dao
interface ImageDao {
    @Query("SELECT * FROM imageitem")
    fun getAll(): List<ImageItem>

    @Query("SELECT * FROM imageitem WHERE title LIKE :title")
    fun findByTitle(title: String): ImageItem

    @Query("SELECT * FROM imageitem WHERE uid LIKE :uid")
    fun findByID(uid: Int): ImageItem

    @Insert
    fun insertAll(vararg imageItems: ImageItem)

    @Delete
    fun delete(imageItem: ImageItem)

    @Update
    fun update(imageItem: ImageItem)
}