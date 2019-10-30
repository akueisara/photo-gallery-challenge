package com.example.photogallerychallenge.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag (
    val type: String,
    val title: String
): Parcelable