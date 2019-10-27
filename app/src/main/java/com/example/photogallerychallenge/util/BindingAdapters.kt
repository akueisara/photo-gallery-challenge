package com.example.photogallerychallenge.util

import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallerychallenge.data.model.Photo
import com.example.photogallerychallenge.ui.photos.PhotosAdapter

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: PagedList<Photo>?) {
    items?.let {
        (listView.adapter as PhotosAdapter).submitList(items)
    }
}