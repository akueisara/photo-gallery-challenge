package com.example.photogallerychallenge.data.repository

import com.example.photogallerychallenge.data.Photo
import com.example.photogallerychallenge.data.Result
import com.example.photogallerychallenge.data.Result.Success
import com.example.photogallerychallenge.data.Result.Error
import com.example.photogallerychallenge.data.Result.UnsplashAPIError
import com.example.photogallerychallenge.data.network.UnsplashApiService
import java.lang.Exception

class UnsplashDataRepository(private val apiService: UnsplashApiService) {

    suspend fun getPhotos(clientId: String?, page: Int?, pageSize: Int?): Result<List<Photo>> {
        try {
            val photos: List<Photo>
            if(clientId != null) {
                photos = apiService.getPhotos(clientId, page, pageSize)
            } else {
                photos = apiService.getPhotos(page = page, pageSize = pageSize)
            }
            return Success(photos)
        } catch (e: Exception) {
            return Error(UnsplashAPIError(e))
        }
    }
}