package com.example.photogallerychallenge.util

import com.example.photogallerychallenge.data.network.UnsplashAPIError


object Converter {
    @JvmStatic
    fun getErrorMessage(error: UnsplashAPIError?): String {
       return error?.message ?: ""
    }
}