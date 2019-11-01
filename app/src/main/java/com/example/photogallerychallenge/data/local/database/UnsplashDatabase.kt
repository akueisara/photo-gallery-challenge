package com.example.photogallerychallenge.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.photogallerychallenge.data.model.DatabasePhoto
import com.example.photogallerychallenge.data.model.DatabaseUser

@Database(
    entities = [DatabasePhoto::class, DatabaseUser::class],
    version = 1,
    exportSchema = false
)
abstract class UnsplashDatabase : RoomDatabase() {

    abstract fun unsplashDao(): UnsplashDao
}