package com.example.photogallerychallenge.data.model

data class Links(
    val self: String,
    val html: String,
    val photos: String?,
    val likes: String?,
    val portfolio: String?,
    val download: String?,
    val download_location: String?,
    val following: String?,
    val followers: String?
)