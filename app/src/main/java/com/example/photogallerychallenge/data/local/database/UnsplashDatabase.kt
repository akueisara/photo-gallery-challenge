package com.example.photogallerychallenge.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {

        @Volatile
        private var INSTANCE: UnsplashDatabase? = null

        fun getInstance(context: Context): UnsplashDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                UnsplashDatabase::class.java, "Unsplash.db")
                .build()
    }
}