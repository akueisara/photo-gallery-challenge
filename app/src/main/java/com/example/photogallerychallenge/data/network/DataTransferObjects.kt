package com.example.photogallerychallenge.data.network

import com.example.photogallerychallenge.data.database.DatabasePhoto
import com.example.photogallerychallenge.data.database.DatabaseUser
import com.example.photogallerychallenge.data.model.Photo
import com.example.photogallerychallenge.data.model.User
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPhotoContainer(val photos: List<Photo>)

fun NetworkPhotoContainer.asDomainModel(): List<Photo> {
    return photos.map {
        Photo(
            id = it.id,
            created_at = it.created_at,
            updated_at = it.updated_at,
            promoted_at = it.promoted_at,
            width = it.width,
            height = it.height,
            color = it.color,
            description = it.description,
            alt_description = it.alt_description,
            urls = it.urls,
            links = it.links,
            likes = it.likes,
            liked_by_user = it.liked_by_user,
            user = it.user
        )
    }
}

fun NetworkPhotoContainer.asDatabaseModel(): Array<DatabasePhoto> {
    return photos.map {
        DatabasePhoto(
            id = it.id,
            created_at = it.created_at,
            updated_at = it.updated_at,
            promoted_at = it.promoted_at,
            width = it.width,
            height = it.height,
            color = it.color,
            description = it.description,
            alt_description = it.alt_description,
            urls = it.urls,
            links = it.links,
            likes = it.likes,
            liked_by_user = it.liked_by_user,
            user_id = it.user.id,
            user_name = it.user.name,
            user_profile_image_url = it.user.profile_image.small
        )
    }.toTypedArray()
}

fun User.asDatabaseModel(): DatabaseUser {
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
