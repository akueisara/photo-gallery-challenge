package com.example.photogallerychallenge.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Links(
    val self: String,
    val html: String,
    val photos: String?,
    @ColumnInfo(name = "link_likes")
    val likes: String?,
    val portfolio: String?,
    val download: String?,
    val download_location: String?,
    val following: String?,
    val followers: String?
) : Parcelable