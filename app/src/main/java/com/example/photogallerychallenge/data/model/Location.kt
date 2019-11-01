package com.example.photogallerychallenge.data.model

import androidx.room.Embedded

data class Location (
    val title: String?,
    val name: String?,
    val city: String?,
    val country: String?,
    @Embedded val position: Position?
)