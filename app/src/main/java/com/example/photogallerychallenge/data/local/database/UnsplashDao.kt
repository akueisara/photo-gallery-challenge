package com.example.photogallerychallenge.data.local.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface UnsplashDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPhotos(vararg photos: DatabasePhoto)

    @Query("SELECT * FROM photos")
    fun getPhotos(): DataSource.Factory<Int, DatabasePhoto>

    @Query("SELECT * FROM photos WHERE id LIKE :photoId")
    fun getPhoto(photoId: String): DatabasePhoto?

    @Update
    fun updatePhoto(photo: DatabasePhoto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: DatabaseUser)

    @Query("SELECT * FROM users WHERE id LIKE :userId")
    fun getUserById(userId: String): DatabaseUser?
}