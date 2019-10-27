package com.example.photogallerychallenge.data

import androidx.paging.PageKeyedDataSource
import com.example.photogallerychallenge.BuildConfig
import com.example.photogallerychallenge.data.model.Photo
import com.example.photogallerychallenge.data.model.Result
import com.example.photogallerychallenge.data.model.Result.Success
import com.example.photogallerychallenge.data.model.Result.Error
import com.example.photogallerychallenge.data.model.Result.UnsplashAPIError
import com.example.photogallerychallenge.data.network.UnsplashApi
import com.example.photogallerychallenge.data.network.UnsplashApiService
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class PhotoDataSource(private val apiService: UnsplashApiService): PageKeyedDataSource<Int, Photo>() {

    private var lastPage: Int? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Photo>) {
        runBlocking { getPhotos(BuildConfig.UNSPLASH_API_ACCESS_KEY, 1, UnsplashApi.DEFAULT_PAGE_SIZE, params, callback) }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        runBlocking { getPhotos(BuildConfig.UNSPLASH_API_ACCESS_KEY, params.key, params.requestedLoadSize, params = params, callback = callback) }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
    }

    suspend fun getPhotos(clientId: String, page: Int? = null, pageSize: Int? = null,
                          initialParams: LoadInitialParams<Int>? = null, initialCallback: LoadInitialCallback<Int, Photo>? = null,
                          params: LoadParams<Int>? = null, callback: LoadCallback<Int, Photo>? = null): Result<List<Photo>> {
        try {
            val response = apiService.getPhotos(clientId, page, pageSize)
            if(response.isSuccessful && response.body() != null) {
                if(initialParams != null) {
                    lastPage = response.headers().get("x-total")?.toInt()?.div(initialParams.requestedLoadSize)
                    initialCallback?.onResult(response.body()!!, null, 2)
                } else if(params != null) {
                    val nextPage = if (params.key == lastPage) null else params.key + 1
                    callback?.onResult(response.body()!!, nextPage)
                }
                return Success(response.body()!!)
            } else {
                return Error(UnsplashAPIError(null, response.code(), response.errorBody()))
            }
        } catch (e: Exception) {
            return Error(UnsplashAPIError(e))
        }
    }
}