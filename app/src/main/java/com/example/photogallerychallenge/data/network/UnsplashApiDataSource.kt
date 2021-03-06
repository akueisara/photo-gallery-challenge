package com.example.photogallerychallenge.data.network

import com.example.photogallerychallenge.BuildConfig
import com.example.photogallerychallenge.data.model.NetworkPhotoContainer
import com.example.photogallerychallenge.data.model.NetworkPhotosContainer
import com.example.photogallerychallenge.data.model.Photo
import com.example.photogallerychallenge.data.model.SearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class UnsplashApiDataSource(private val service: UnsplashApiService): UnsplashRemoteDataSource {
    override fun loadPhotos(
        page: Int,
        itemsPerPage: Int,
        query: String,
        onSuccess: (networkPhotosContainer: NetworkPhotosContainer) -> Unit,
        onError: (UnsplashAPIError) -> Unit) {

        Timber.d("page: $page, itemsPerPage: $itemsPerPage")


        if(query.isEmpty()) {
            service.getPhotos(page, itemsPerPage).enqueue(
                object : Callback<List<Photo>> {
                    override fun onFailure(call: Call<List<Photo>>?, t: Throwable) {
                        val error = UnsplashAPIError(t)
                        Timber.e("fail to get photos, error: ${error.message}")
                        onError(error)
                    }

                    override fun onResponse(
                        call: Call<List<Photo>>?,
                        response: Response<List<Photo>>
                    ) {
                        if (response.isSuccessful) {
                            val repos = response.body() ?: emptyList()
                            onSuccess(
                                NetworkPhotosContainer(repos)
                            )
                        } else {
                            val error = UnsplashAPIError(
                                errorCode = response.code(),
                                errorBody = response.errorBody()
                            )
                            Timber.e("error -- code: ${error.code}, message: ${error.message}")
                            onError(error)
                        }
                    }
                })
        } else {
            service.searchPhotos(page, itemsPerPage, query).enqueue(
                object : Callback<SearchResult> {
                    override fun onFailure(call: Call<SearchResult>?, t: Throwable) {
                        val error = UnsplashAPIError(t)
                        Timber.e("fail to get photos, error: ${error.message}")
                        onError(error)
                    }

                    override fun onResponse(
                        call: Call<SearchResult>?,
                        response: Response<SearchResult>
                    ) {
                        if (response.isSuccessful) {
                            val repos = response.body()?.results ?: emptyList()
                            onSuccess(
                                NetworkPhotosContainer(repos)
                            )
                        } else {
                            val error = UnsplashAPIError(
                                errorCode = response.code(),
                                errorBody = response.errorBody()
                            )
                            Timber.e("error -- code: ${error.code}, message: ${error.message}")
                            onError(error)
                        }
                    }
                })
        }
    }

    override suspend fun likePhoto(photoId: String, onSuccess: (networkPhotoContainer: NetworkPhotoContainer) -> Unit,
                           onError: (UnsplashAPIError) -> Unit) {
        try {
            val result = service.likePhoto(photoId).await()
            onSuccess(NetworkPhotoContainer(result.photo))
        } catch (e: Exception) {
            val error = UnsplashAPIError(e)
            Timber.d( "error: ${error.code} ${error.message}")
            onError(error)
        }
    }

    override suspend fun unlikePhoto(photoId: String, onSuccess: (networkPhotoContainer: NetworkPhotoContainer) -> Unit,
                             onError: (UnsplashAPIError) -> Unit) {
        try {
            val result = service.unlikePhoto(photoId).await()
            onSuccess(NetworkPhotoContainer(result.photo))
        } catch (e: Exception) {
            val error = UnsplashAPIError(e)
            Timber.d( "error: ${error.code} ${error.message}")
            onError(error)
        }
    }

    override suspend fun getPhoto(photoId: String,
                         onSuccess: (networkPhotoContainer: NetworkPhotoContainer) -> Unit,
                         onError: (error: UnsplashAPIError) -> Unit) {

        Timber.d("get photo $photoId from Network")

        try {
            val photo = service.getPhoto(photoId).await()
            onSuccess(NetworkPhotoContainer(photo))
        } catch (e: Exception) {
            Timber.d( "fail to get data")
            val error = UnsplashAPIError(e)
            Timber.d( "error: ${error.code} ${error.message}")
            onError(error)
        }
    }
}