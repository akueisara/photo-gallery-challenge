package com.example.photogallerychallenge.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UnsplashDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotos(vararg photos: DatabasePhoto)

    @Query("SELECT * FROM photos")
    fun getPhotos(): DataSource.Factory<Int, DatabasePhoto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: DatabaseUser)

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: String): DatabaseUser?
}
