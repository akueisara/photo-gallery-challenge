package com.example.photogallerychallenge.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Position(
    val latitude: Double?,
    val longitude: Double?
): Parcelable