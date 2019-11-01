package com.example.photogallerychallenge.data.network

import com.example.photogallerychallenge.BuildConfig
import com.example.photogallerychallenge.data.model.Photo
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import timber.log.Timber
import java.util.concurrent.TimeUnit
import com.example.photogallerychallenge.data.model.NetworkPhotoContainer
import com.example.photogallerychallenge.data.model.NetworkPhotosContainer
import kotlinx.coroutines.Deferred
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull


interface UnsplashApiService {

    @GET("photos")
    fun getPhotos(@Query("client_id") clientId: String,
                  @Query("page") page: Int?,
                  @Query("per_page") pageSize: Int?): Call<List<Photo>>

    @GET("photos/{id}")
    fun getPhoto(@Path("id") id: String, @Query("client_id") clientId: String): Deferred<Photo>
}

object UnsplashApi {
    private const val BASE_URL = "https://api.unsplash.com/"
    private const val API_VER = "v1"

    private const val CONTENT_TYPE = "Content-Type"
    private const val APPLICATION_JSON = "application/json"
    private const val ACCEPT_VERSION = "Accept-Version"

    val unsplashApiService : UnsplashApiService by lazy { create() }

    val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .addHeader(ACCEPT_VERSION, API_VER)
                .build()
            chain.proceed(newRequest)
        }
        .callTimeout(2, TimeUnit.MINUTES)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun create(): UnsplashApiService = create(BASE_URL.toHttpUrlOrNull()!!)
    fun create(httpUrl: HttpUrl): UnsplashApiService {
        return Retrofit.Builder()
            .baseUrl(httpUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(UnsplashApiService::class.java)
    }
}