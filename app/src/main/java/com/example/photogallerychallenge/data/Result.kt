package com.example.photogallerychallenge.data

import com.example.photogallerychallenge.data.Result.Success
import com.example.photogallerychallenge.data.network.UnsplashApi
import com.squareup.moshi.JsonEncodingException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: UnsplashAPIError) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[code=${error.code ?: ""}, message=${error.message}]"
            Loading -> "Loading"
        }
    }

    class UnsplashAPIError(error: Throwable) {
        var code: Int? = null
        var message = "An error occurred"

        companion object {
            const val SERVER_CONNECTION_ERROR = "Cannot connect to the server."
            const val TIME_OUT_ERROR = "Request timed out."
        }

        init {
            if (error is HttpException) {
                this.code = error.response()?.code()
                val errorJsonString = error.response()?.errorBody()?.string() ?: ""
                try {
                    this.message = UnsplashApi.moshi.adapter(
                        Errors::class.java).fromJson(errorJsonString)?.errors?.get(0)!!
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
                this.message = error.message ?: this.message
            }
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Success && data != null