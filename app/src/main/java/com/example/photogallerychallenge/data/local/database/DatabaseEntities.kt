package com.example.photogallerychallenge.data.local.database

import androidx.room.*
import com.example.photogallerychallenge.data.model.*

@Entity(tableName = "photos")
data class DatabasePhoto constructor(
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
    var user_profile_image_url: String?,
    @Embedded var exif: ExtraInfo?,
    @Embedded val location: Location?,
    val views: Long?,
    val downloads: Long?)

@Entity(tableName = "users")
data class DatabaseUser constructor(
    @PrimaryKey val id: String,
    val updated_at: String,
    val username: String,
    val name: String,
    val twitter_username: String?,
    val portfolio_url: String?,
    val bio: String?,
    val location: String?,
    @Embedded(prefix = "user_") val links: Links,
    @Embedded(prefix = "user_") val profile_image: Urls,
    val total_likes: Int,
    val total_photos: Int,
    val total_collections: Int,
    val accepted_tos: Boolean
)