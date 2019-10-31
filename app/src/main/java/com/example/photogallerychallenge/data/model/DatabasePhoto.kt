package com.example.photogallerychallenge.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class DatabasePhoto(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val created_at: String,
    val updated_at: String,
    val promoted_at: String?,
    val width: Int,
    val height: Int,
    val color: String? = "#000000",
    val description: String?,
    val alt_description: String?,
    @Embedded val urls: Urls,
    @Embedded val links: Links,
    val likes: Int,
    var liked_by_user: Boolean,
    val user_id: String,
    var user_name: String,
    var user_username: String,
    var user_profile_image_url: String?,
    @Embedded var exif: ExtraInfo?,
    @Embedded val location: Location?,
    val views: Long?,
    val downloads: Long?)