package com.example.photogallerychallenge.data.network

import com.example.photogallerychallenge.BuildConfig
import com.example.photogallerychallenge.data.Photo
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface UnsplashApiService {

    @GET("photos")
    suspend fun getPhotos(@Query("client_id") clientId: String = BuildConfig.UNSPLASH_API_ACCESS_KEY,
                  @Query("page") page: Int?,
                  @Query("per_page") pageSize: Int?): List<Photo>
}

object UnsplashApi {
    const val BASE_URL = "https://api.unsplash.com/"
    private const val API_VER = "v1"

    private const val CONTENT_TYPE = "Content-Type"
    private const val APPLICATION_JSON = "application/json"
    private const val ACCEPT_VERSION = "Accept-Version"
//    private const val AUTHORIZATION = "Authorization"

    val unsplashApiService : UnsplashApiService by lazy { create() }

    val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .addHeader(ACCEPT_VERSION, API_VER)
//                .addHeader(AUTHORIZATION, BuildConfig.UNSPLASH_API_ACCESS_KEY)
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

    fun create(): UnsplashApiService =
        create(HttpUrl.parse(BASE_URL)!!)
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