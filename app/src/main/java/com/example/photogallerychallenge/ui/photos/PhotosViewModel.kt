package com.example.photogallerychallenge.ui.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.PhotoDataSourceFactory
import com.example.photogallerychallenge.data.model.Photo
import com.example.photogallerychallenge.data.network.UnsplashApiService
import androidx.paging.LivePagedListBuilder
import com.example.photogallerychallenge.data.network.UnsplashApi

class PhotosViewModel(apiService: UnsplashApiService) : ViewModel() {

    private val factory = PhotoDataSourceFactory(apiService)

    var photos: LiveData<PagedList<Photo>>

    init {
        photos = LivePagedListBuilder(factory, PagedList.Config.Builder().setPageSize(UnsplashApi.DEFAULT_PAGE_SIZE).build()).build()
    }
}