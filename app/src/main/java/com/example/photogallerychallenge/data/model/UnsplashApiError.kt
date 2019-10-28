package com.example.photogallerychallenge.data.model

import com.example.photogallerychallenge.data.network.UnsplashApi
import com.squareup.moshi.JsonEncodingException
import okhttp3.ResponseBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UnsplashAPIError(error: Throwable? = null, errorCode: Int? = null, errorBody: ResponseBody? = null) {
    var code: Int? = null
    var message = "An apiError occurred"

    companion object {
        const val SERVER_CONNECTION_ERROR = "There was a network error.\nPlease check your internet connection."
        const val TIME_OUT_ERROR = "Request timed out."
    }

    init {
        if(errorCode != null && errorBody != null) {
            this.code = errorCode
            val errorJsonString = errorBody.string()
            try {
                this.message = UnsplashApi.moshi.adapter(Errors::class.java).fromJson(errorJsonString)?.errors?.get(0)!!
            } catch (e: JsonEncodingException) {
                this.message = errorJsonString
            }
        } else if(error is UnknownHostException) {
            this.message =
                SERVER_CONNECTION_ERROR
        } else if(error is SocketTimeoutException) {
            this.message =
                TIME_OUT_ERROR
        } else {
            this.message = error?.message ?: this.message
        }
    }
}