package com.example.photogallerychallenge.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import com.example.photogallerychallenge.ui.photos.PhotoViewType

class PreferencesHelper(val context: Context) {
    companion object {
        const val PREF_PHOTO_VIEW_TYPE = "photo_view_type"
        const val PREF_LAST_QUERY = "photo_last_query"
    }

    private val PREFS_NAME = "com.example.photogallerychallenge"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getPhotoViewType(): Int {
        return sharedPref.getInt(PREF_PHOTO_VIEW_TYPE, PhotoViewType.LIST.value)
    }

    fun setPhotoViewType(photoViewType: Int) {
        sharedPref.edit().putInt(PREF_PHOTO_VIEW_TYPE, photoViewType).apply()
    }

    fun getLastQuery(): String {
        return sharedPref.getString(PREF_LAST_QUERY, "") ?: ""
    }

    fun setLastQuery(queryString: String) {
        sharedPref.edit().putString(PREF_LAST_QUERY, queryString).apply()
    }

}