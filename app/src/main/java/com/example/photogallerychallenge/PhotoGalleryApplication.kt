package com.example.photogallerychallenge

import android.app.Application
import com.example.photogallerychallenge.repository.Repository
import timber.log.Timber

class PhotoGalleryApplication : Application() {

    val unsplashRepository: Repository
        get() = ServiceLocator.provideUnsplashRepository(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
