package com.example.photogallerychallenge.ui.photos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.photogallerychallenge.getFakeDatabasePhoto
import photogallerychallenge.getOrAwaitValue
import com.example.photogallerychallenge.repository.FakeRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotosViewModelTest {

    private lateinit var photosViewModel: PhotosViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var fakeRepository: FakeRepository

    // Executes each task synchronously using Architecture Components.
    // include this rule when writing tests testing LiveData
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
//    @ExperimentalCoroutinesApi
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        fakeRepository = FakeRepository()
        val photo1 = getFakeDatabasePhoto("1yQEs9ZumxU")
        val photo2 = getFakeDatabasePhoto("2yQEs9ZumxU")
        val photo3 = getFakeDatabasePhoto("3yQEs9ZumxU")
        fakeRepository.addDatabasePhotos(photo1, photo2, photo3)
        // Given a fresh ViewModel
        photosViewModel = PhotosViewModel(fakeRepository)
    }

    @Test
    fun openPhoto_setsOpenPhotoEvent() {

        photosViewModel.openPhoto("1yQEs9ZumxU")

        // Then the open photo event is triggered
        val value = photosViewModel.openPhotoEvent.getOrAwaitValue()

        MatcherAssert.assertThat(value.getContentIfNotHandled(), CoreMatchers.not(CoreMatchers.nullValue()))
    }

    @Test
    fun loadPhotos_setsLoadPhotosResult() {

        photosViewModel.loadPhotos("")

        val loadPhotosResult = photosViewModel.loadPhotosResult.getOrAwaitValue()

        MatcherAssert.assertThat(loadPhotosResult, CoreMatchers.not(CoreMatchers.nullValue()))

        val photos = photosViewModel.photos.getOrAwaitValue()

        MatcherAssert.assertThat(photos, CoreMatchers.not(CoreMatchers.nullValue()))
    }
}