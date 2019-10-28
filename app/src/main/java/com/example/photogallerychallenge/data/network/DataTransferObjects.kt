package com.example.photogallerychallenge.data.network

import com.example.photogallerychallenge.data.database.DatabasePhoto
import com.example.photogallerychallenge.data.model.Photo
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

fun NetworkPhotoContainer.asDatabaseModel(): List<DatabasePhoto> {
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
            user_id = it.user.id
        )
    }.toMutableList()
}
