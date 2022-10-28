package com.example.imagesearchproject.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ImageItem::class], version = 1)
abstract class ImageDatabase: RoomDatabase() {
    abstract fun imageDao(): ImageDao

    companion object {
        private var instance: ImageDatabase? = null

        // 싱글톤 사용
        @Synchronized
        fun getInstance(context: Context): ImageDatabase? {
            if (instance == null) {
                synchronized(ImageDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ImageDatabase::class.java,
                        "imageitem"
                    ).build()
                }
            }
            return instance
        }
    }
}