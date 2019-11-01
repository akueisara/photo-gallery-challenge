package com.example.photogallerychallenge.util

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
import android.net.Uri
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.photogallerychallenge.data.model.DatabasePhoto
import timber.log.Timber
import com.example.photogallerychallenge.R
import com.example.photogallerychallenge.data.model.Location
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.github.piasy.biv.view.BigImageView
import java.text.NumberFormat
import java.util.*

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: PagedList<DatabasePhoto>?) {
    items?.let {
        Timber.d("items size: ${it.size}")
        (listView.adapter as PhotosAdapter).submitList(items)
    }
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
}

@BindingAdapter("bigImageViewUrl")
fun setBigImageView(bigImageView: BigImageView, url: String?) {
    url?.let {
        bigImageView.showImage(Uri.parse(url));
    }
}


@BindingAdapter("roundImageUrl")
fun setRoundImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_default_avatar)
            .into(imageView)
    }
}

@BindingAdapter("backgroundHexColor")
fun setHexColor(view: View, hex: String?) {
    try {
        hex?.let { view.setBackgroundColor(Color.parseColor(hex)) }
    } catch (e: Exception) {
        Timber.e(e)
        view.setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.black))
    }
}


// Photo Details
@BindingAdapter("infoText")
fun setInfoText(textView: TextView, text: String?) {
    textView.text = text ?: "None"
}

@BindingAdapter("infoNumber")
fun setInfoNumber(textView: TextView, number: Long) {
    textView.text = NumberFormat.getNumberInstance(Locale.US).format(number)
}

@BindingAdapter("infoLocation")
fun setInfoLocation(textView: TextView, location: Location?) {
    if(location?.city != null && location.country != null) {
        textView.text = textView.context.getString(R.string.location_des, location.city, location.country)
    } else if(location?.city != null) {
        textView.text = location.city
    } else {
        textView.text = location?.country
    }

}

