package com.example.photogallerychallenge.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.photogallerychallenge.data.local.database.FakeUnsplashLocalDataSource
import com.example.photogallerychallenge.data.network.FakeUnsplashRemoteDataSource
import com.example.photogallerychallenge.getFakeDatabasePhoto
import com.example.photogallerychallenge.getFakePhoto
import photogallerychallenge.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UnsplashRepositoryTest {

    private lateinit var unsplashLocalDataSource: FakeUnsplashLocalDataSource
    private lateinit var unsplashRemoteDataSource: FakeUnsplashRemoteDataSource

    // Class under test
    private lateinit var unsplashRepository: UnsplashRepository

    private val photo1 = getFakePhoto("photo1")
    private val photo2 = getFakePhoto("photo2")
    private val datebasePhoto1 = getFakeDatabasePhoto("photo1")
    private val datebasePhoto2 = getFakeDatabasePhoto("photo2")
    private val remotePhotos = listOf(photo1, photo2).sortedBy { it.id }
    private val localPhotos = listOf(datebasePhoto1, datebasePhoto2).sortedBy { it.id }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createRepository() {
        unsplashRemoteDataSource = FakeUnsplashRemoteDataSource(remotePhotos.toMutableList())
        unsplashLocalDataSource = FakeUnsplashLocalDataSource(localPhotos.toMutableList())
        // Get a reference to the class under test
        unsplashRepository = UnsplashRepository(unsplashRemoteDataSource, unsplashLocalDataSource)
    }

    @Test
    fun loadPhotos_requestsAllPhotosFromDataSource() {
        // When photos are requested from the unsplash repository
        val loadPhotosResult = unsplashRepository.loadPhotos()

        val databasePhotoPagedList = loadPhotosResult.data.getOrAwaitValue()

        assertThat(databasePhotoPagedList, IsEqual(localPhotos))
    }

    @Test
    fun reloadPhotos_requestsPhotosOfNextPageFromDataSource() {
       // TODO
    }

    @Test
    fun loadPhoto_requestsPhotoFromDataSource() = runBlocking {
        // When photos are requested from the unsplash repository
        val loadPhotosResult = unsplashRepository.loadPhoto("photo2")

        val databasePhoto = loadPhotosResult.data.getOrAwaitValue()

        assertThat(databasePhoto.id, IsEqual(remotePhotos[1].id))
    }
}