package com.example.photogallerychallenge

import android.app.Application
import timber.log.Timber

class PhotoGalleryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
