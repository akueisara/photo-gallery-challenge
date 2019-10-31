package com.example.photogallerychallenge.data.model

import com.squareup.moshi.Json
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExtraInfo (
    val make: String?,
    val model: String?,
    @Json(name = "exposure_time") val exposureTime: String?,
    val aperture: String?,
    @Json(name = "focal_length") val focalLength: String?,
    val iso: Int?
): Parcelable