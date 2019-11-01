package com.example.photogallerychallenge.api

import com.example.photogallerychallenge.BuildConfig
import com.example.photogallerychallenge.data.model.*
import com.example.photogallerychallenge.data.network.UnsplashAPIError
import com.example.photogallerychallenge.data.model.NetworkPhotosContainer
import com.example.photogallerychallenge.data.network.UnsplashApi
import com.example.photogallerychallenge.data.network.UnsplashApiService
import com.example.photogallerychallenge.readContentFromFile
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Test

import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection
import java.io.*
import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class UnsplashApiServiceTest {

    val RES_BASE_PATH = "../app/src/test/res/"

    private var mockWebServer = MockWebServer()

    private lateinit var apiService: UnsplashApiService

    @Before
    fun setup() {
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(UnsplashApi.moshi))
            .client(UnsplashApi.client)
            .build()
            .create(UnsplashApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun unsplashApiService_response_401Unauthorized() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
            .setBody(readContentFromFile(RES_BASE_PATH + "authorization_error_response.json"))

        mockWebServer.enqueue(response)

        getPhotos("wrong_access_key", onError = {
            assertThat(it.code, `is`(HttpURLConnection.HTTP_UNAUTHORIZED))
            assertThat(it.message, `is`("OAuth apiError: The access token is invalid"))
        })

    }

    @Test
    fun unsplashApiService_response_503ServerUnavailable() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAVAILABLE)
            .setBody(readContentFromFile(RES_BASE_PATH + "server_unavailable_response.html"))

        mockWebServer.enqueue(response)

        val result = runBlocking { }

        getPhotos(page = 1000000000, onError = {
            assertThat(it.code, `is`(HttpURLConnection.HTTP_UNAVAILABLE))
            assertThat(
                it.message,
                `is`("We are experiencing errors. Please check https://status.unsplash.com for updates.")
            )

        })
    }

    @Test
    fun unsplashApiService_response_TimeOut() {
        val response = MockResponse()
            .setSocketPolicy(SocketPolicy.NO_RESPONSE)

        mockWebServer.enqueue(response)

        getPhotos(page = 1000000000, onError = {
            assertThat(it.message, `is`(UnsplashAPIError.TIME_OUT_ERROR))
        })
    }

    @Test
    fun getPhotos_singlePhoto_OK() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(readContentFromFile(RES_BASE_PATH + "get_photos_single_photo_response.json"))
        mockWebServer.enqueue(response)

        getPhotos(page = 1, onSuccess = {
            val photos = it.photos
            assertThat(photos.size, `is`(1))

            val urls = Urls(
                "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjk3OTUyfQ",
                "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjk3OTUyfQ",
                null,
                "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjk3OTUyfQ",
                null,
                "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjk3OTUyfQ",
                "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjk3OTUyfQ"
            )

            val links = Links(
                "https://api.unsplash.com/photos/TYpX940GS_U",
                "https://unsplash.com/photos/TYpX940GS_U",
                null,
                null,
                null,
                "https://unsplash.com/photos/TYpX940GS_U/download",
                "https://api.unsplash.com/photos/TYpX940GS_U/download",
                null,
                null
            )

            val userLinks = Links(
                "https://api.unsplash.com/users/danesduet",
                "https://unsplash.com/@danesduet",
                "https://api.unsplash.com/users/danesduet/photos",
                "https://api.unsplash.com/users/danesduet/likes",
                "https://api.unsplash.com/users/danesduet/portfolio",
                null,
                null,
                "https://api.unsplash.com/users/danesduet/following",
                "https://api.unsplash.com/users/danesduet/followers"
            )

            val userImageUrls = Urls(
                null,
                "https://images.unsplash.com/profile-1528009924690-123902d73e88?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32",
                "https://images.unsplash.com/profile-1528009924690-123902d73e88?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64",
                null,
                "https://images.unsplash.com/profile-1528009924690-123902d73e88?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128",
                null,
                null
            )

            val user = User(
                "WqEryhdhOsY",
                "2019-10-26T04:42:06-04:00",
                "danesduet",
                "Daniel Olah",
                "danesduet",
                null,
                "Capturing the future",
                "Budapest, Hungary",
                userLinks,
                userImageUrls,
                107,
                94,
                0,
                true
            )

            assertThat(photos[0], `is`(
                Photo(
                    "TYpX940GS_U", "2019-10-26T02:47:19-04:00",
                    "2019-10-26T04:23:34-04:00", "2019-10-26T04:23:34-04:00",
                    2026, 3602, "#233961", "Infinity Dunes - Abu Dhabi Desert", null,
                    urls, links, 70, false, user, null, null, null, null
                )
            ))
        })
    }

    @Test
    fun getPhotos_tenPhotos_OK() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(readContentFromFile(RES_BASE_PATH + "get_photos_ten_photos_response.json"))
        mockWebServer.enqueue(response)

        getPhotos(page = 10, onSuccess = {
            val photos = it.photos
            assertThat(photos.size, `is`(10))
        })
    }

    fun getPhotos(clientId: String = BuildConfig.UNSPLASH_API_ACCESS_KEY, page: Int? = null, pageSize: Int? = null, onSuccess: ((networkPhotosContainer: NetworkPhotosContainer) -> Unit)? = null,
                  onError: ((error: UnsplashAPIError) -> Unit)? = null) {
        apiService.getPhotos(clientId, page, pageSize).enqueue(
            object : Callback<List<Photo>> {
                override fun onFailure(call: Call<List<Photo>>?, t: Throwable) {
                    Timber.d( "fail to get data")
                    onError?.let { it(
                        UnsplashAPIError(t)
                    ) }
                }

                override fun onResponse(
                    call: Call<List<Photo>>?,
                    response: Response<List<Photo>>
                ) {
                    Timber.d("got a response $response")
                    if (response.isSuccessful) {
                        val repos = response.body() ?: emptyList()
                        onSuccess?.let { it(
                            NetworkPhotosContainer(
                                repos
                            )
                        ) }
                    } else {
                        onError?.let { it(
                            UnsplashAPIError(
                                errorCode = response.code(),
                                errorBody = response.errorBody()
                            )
                        ) }
                    }
                }
            })
    }
}