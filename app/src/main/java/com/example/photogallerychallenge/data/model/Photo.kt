package com.example.photogallerychallenge.data.model

data class Photo(
    val id: String,
    val created_at: String,
    val updated_at: String,
    val promoted_at: String?,
    val width: Int,
    val height: Int,
    val color: String? = "#000000",
    val description: String?,
    val alt_description: String?,
    val urls: Urls,
    val links: Links,
    val likes: Int,
    var liked_by_user: Boolean,
    val user: User?,
    val exif: ExtraInfo?,
    val location: Location?,
    val views: Long?,
    val downloads: Long?
)