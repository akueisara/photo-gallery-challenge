package com.example.photogallerychallenge.data.model

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location (
    val title: String?,
    val name: String?,
    val city: String?,
    val country: String?,
    @Embedded val position: Position
): Parcelable