package com.example.photogallerychallenge.data.database

import androidx.lifecycle.Transformations.map
import androidx.room.*
import com.example.photogallerychallenge.data.model.Links
import com.example.photogallerychallenge.data.model.Photo
import com.example.photogallerychallenge.data.model.Urls
import com.example.photogallerychallenge.data.model.User

@Entity(tableName = "photos",
    foreignKeys = arrayOf(ForeignKey(
        entity = DatabaseUser::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("user_id"))))
data class DatabasePhoto constructor(
    @PrimaryKey val id: String,
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
    val user_id: String)

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

fun User.asDomainModel(): DatabaseUser {
    return DatabaseUser(
            id = id,
            updated_at = updated_at,
            username = username,
            name = name,
            twitter_username = twitter_username,
            portfolio_url = portfolio_url,
            bio = bio,
            location = location,
            links = links,
            profile_image = profile_image,
            total_likes = total_likes ,
            total_photos = total_photos,
            total_collections = total_collections,
            accepted_tos = accepted_tos)
}

fun DatabaseUser.asDomainModel(): User {
    return User(
        id = id,
        updated_at = updated_at,
        username = username,
        name = name,
        twitter_username = twitter_username,
        portfolio_url = portfolio_url,
        bio = bio,
        location = location,
        links = links,
        profile_image = profile_image,
        total_likes = total_likes ,
        total_photos = total_photos,
        total_collections = total_collections,
        accepted_tos = accepted_tos)
}