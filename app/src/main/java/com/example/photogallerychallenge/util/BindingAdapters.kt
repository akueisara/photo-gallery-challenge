package com.example.photogallerychallenge.util

import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.photogallerychallenge.ui.photos.PhotosAdapter
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.photogallerychallenge.data.local.database.DatabasePhoto
import timber.log.Timber
import com.example.photogallerychallenge.R

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: PagedList<DatabasePhoto>?) {
    items?.let {
        (listView.adapter as PhotosAdapter).submitList(items)
    }
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView)
}


@BindingAdapter("roundImageUrl")
fun setRoundImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .placeholder(R.drawable.ic_default_avatar)
        .into(imageView)
}

@BindingAdapter("backgroundHexColor")
fun setHexColor(view: View, hex: String) {
    try {
        view.setBackgroundColor(Color.parseColor(hex))
    } catch (e: Exception) {
        Timber.e(e)
        view.setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.black))
    }
}