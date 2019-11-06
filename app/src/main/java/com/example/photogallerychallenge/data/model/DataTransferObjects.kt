package com.example.photogallerychallenge.data.model

import com.squareup.moshi.JsonClass

// PHOTOS
@JsonClass(generateAdapter = true)
data class NetworkPhotosContainer(val photos: List<Photo>)

fun NetworkPhotosContainer.asDomainModel(): List<Photo> {
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
            user = it.user,
            exif = it.exif,
            location = it.location,
            views =  it.views,
            downloads= it.downloads
        )
    }
}

fun NetworkPhotosContainer.asDatabaseModel(): Array<DatabasePhoto> {
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
            user_id = it.user?.id ?: "",
            user_name = it.user?.name ?: "",
            user_username = it.user?.username ?: "",
            user_profile_image_url = it.user?.profile_image?.small,
            exif = it.exif,
            location = it.location,
            views = it.views,
            downloads = it.downloads
        )
    }.toTypedArray()
}

// PHOTO
@JsonClass(generateAdapter = true)
data class NetworkPhotoContainer(val photo: Photo)

fun NetworkPhotoContainer.asDatabaseModel(): DatabasePhoto {
    return DatabasePhoto(
        id = photo.id,
        created_at = photo.created_at,
        updated_at = photo.updated_at,
        promoted_at = photo.promoted_at,
        width = photo.width,
        height = photo.height,
        color = photo.color,
        description = photo.description,
        alt_description = photo.alt_description,
        urls = photo.urls,
        links = photo.links,
        likes = photo.likes,
        liked_by_user = photo.liked_by_user,
        user_id = photo.user?.id ?: "",
        user_name = photo.user?.name ?: "",
        user_username = photo.user?.username ?: "",
        user_profile_image_url = photo.user?.profile_image?.small ?: "",
        exif = photo.exif,
        location = photo.location,
        views = photo.views,
        downloads = photo.downloads
    )
}

// USER
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
        total_likes = total_likes,
        total_photos = total_photos,
        total_collections = total_collections,
        accepted_tos = accepted_tos
    )
}