package com.example.photogallerychallenge.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.photogallerychallenge.data.model.Photo
import com.example.photogallerychallenge.data.model.User

@Dao
interface UnsplashDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotos(photos: List<DatabasePhoto>)

    @Query("SELECT * FROM photos")
    suspend fun getPhotos(): DataSource.Factory<Int, DatabasePhoto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: DatabaseUser)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): DatabaseUser?
}
