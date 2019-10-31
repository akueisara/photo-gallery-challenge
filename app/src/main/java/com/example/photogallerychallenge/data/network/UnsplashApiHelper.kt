package com.example.photogallerychallenge.data.network

import com.example.photogallerychallenge.BuildConfig
import com.example.photogallerychallenge.data.model.NetworkPhotoContainer
import com.example.photogallerychallenge.data.model.NetworkPhotosContainer
import com.example.photogallerychallenge.data.model.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object UnsplashApiHelper {
    fun loadPhotos(
        service: UnsplashApiService,
        page: Int,
        itemsPerPage: Int,
        onSuccess: (networkPhotosContainer: NetworkPhotosContainer) -> Unit,
        onError: (UnsplashAPIError) -> Unit) {

        Timber.d("page: $page, itemsPerPage: $itemsPerPage")

        service.getPhotos(BuildConfig.UNSPLASH_API_ACCESS_KEY, page, itemsPerPage).enqueue(
            object : Callback<List<Photo>> {
                override fun onFailure(call: Call<List<Photo>>?, t: Throwable) {
                    val error = UnsplashAPIError(t)
                    Timber.e( "fail to get photos, error: ${error.message}")
                    onError(error)
                }

                override fun onResponse(
                    call: Call<List<Photo>>?,
                    response: Response<List<Photo>>
                ) {
                    if (response.isSuccessful) {
                        val repos = response.body() ?: emptyList()
                        onSuccess(NetworkPhotosContainer(repos)
                        )
                    } else {
                        val error = UnsplashAPIError(errorCode = response.code(), errorBody = response.errorBody())
                        Timber.e( "error -- code: ${error.code}, message: ${error.message}")
                        onError(error)
                    }
                }
            })
    }

    suspend fun getPhoto(service: UnsplashApiService, photoId: String,
                         onSuccess: (networkPhotoContainer: NetworkPhotoContainer) -> Unit,
                         onError: (error: UnsplashAPIError) -> Unit) {

        Timber.d("get photo $photoId from Network")

        try {
            val photo = service.getPhoto(photoId, BuildConfig.UNSPLASH_API_ACCESS_KEY).await()
            Timber.d("got a response $photo")
            onSuccess(NetworkPhotoContainer(photo))
        } catch (e: Exception) {
            Timber.d( "fail to get data")
            val error = UnsplashAPIError(e)
            Timber.d( "error: ${error.code} ${error.message}")
            onError(error)
        }
    }
}