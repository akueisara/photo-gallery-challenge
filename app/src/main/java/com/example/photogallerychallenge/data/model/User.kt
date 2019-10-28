package com.example.photogallerychallenge.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String,
    val updated_at: String,
    val username: String,
    val name: String,
    val twitter_username: String?,
    val portfolio_url: String?,
    val bio: String?,
    val location: String?,
    val links: Links,
    val profile_image: Urls,
    val total_likes: Int,
    val total_photos: Int,
    val total_collections: Int,
    val accepted_tos: Boolean
): Parcelable