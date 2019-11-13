package com.example.photogallerychallenge.data.local.database

import androidx.paging.DataSource
import androidx.room.*
import com.example.photogallerychallenge.data.model.DatabasePhoto
import com.example.photogallerychallenge.data.model.DatabaseUser

@Dao
interface UnsplashDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotos(vararg photos: DatabasePhoto)

//    @Query("SELECT * FROM photos ORDER BY liked_by_user DESC")
    @Query("SELECT * FROM photos")
    fun getPhotos(): DataSource.Factory<Int, DatabasePhoto>

    @Query("SELECT * FROM photos WHERE id LIKE :photoId")
    fun getPhoto(photoId: String): DatabasePhoto?

    @Update
    fun updatePhoto(photo: DatabasePhoto)

    @Update
    fun updatePhotoLike(photo: DatabasePhoto)

    @Query("DELETE FROM photos")
    fun clearPhotos()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: DatabaseUser)

    @Query("SELECT * FROM users WHERE id LIKE :userId")
    fun getUserById(userId: String): DatabaseUser?
}
