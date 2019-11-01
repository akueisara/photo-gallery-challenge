package com.example.photogallerychallenge.data.model

import androidx.room.*

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