package com.example.photogallerychallenge.ui.photodetail

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.example.photogallerychallenge.R
import com.example.photogallerychallenge.databinding.DialogPhotoViewerBinding
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader

class PhotoViewerDialog(context: Context, photoImageUrl: String) : Dialog(context) {

    private var binding: DialogPhotoViewerBinding

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(true)
        BigImageViewer.initialize(GlideImageLoader.with(context))
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_photo_viewer, null, false)
        setContentView(binding.root)
        binding.photoImageUrl = photoImageUrl
    }
}