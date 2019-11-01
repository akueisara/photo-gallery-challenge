package com.example.photogallerychallenge.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.model.DatabasePhoto
import com.example.photogallerychallenge.data.network.UnsplashAPIError

data class Result<T>(val data: LiveData<T>, val dataLoading: LiveData<Boolean>?, val error: LiveData<UnsplashAPIError?>)