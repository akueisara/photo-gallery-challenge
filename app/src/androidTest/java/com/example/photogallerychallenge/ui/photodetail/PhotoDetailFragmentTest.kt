package com.example.photogallerychallenge.ui.photodetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.example.photogallerychallenge.R
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.photogallerychallenge.ServiceLocator
import com.example.photogallerychallenge.getFakeDatabasePhoto
import com.example.photogallerychallenge.repository.FakeRepository
import com.example.photogallerychallenge.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi // for coroutine
class PhotoDetailFragmentTest {

    private lateinit var repository: Repository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initRepository() {
        repository = FakeRepository()
        val photo1 = getFakeDatabasePhoto("1yQEs9ZumxU")
        val photo2 = getFakeDatabasePhoto("TYpX940GS_U")
        val photo3 = getFakeDatabasePhoto("3yQEs9ZumxU")
        (repository as FakeRepository).addDatabasePhotos(photo1, photo2, photo3)
        ServiceLocator.unsplashRepository = repository
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun photoDetails_DisplayedInUi() = runBlockingTest {
        val photoId = "TYpX940GS_U"
        val bundle = PhotoDetailFragmentArgs(photoId).toBundle()
        launchFragmentInContainer<PhotoDetailFragment>(bundle, R.style.AppTheme)

        // GIVEN - Load photo
        repository.loadPhoto(photoId)

        // THEN - photos details are displayed on the screen
        // make sure that the title/description are both shown and correct
        Espresso.onView(ViewMatchers.withId(R.id.photo_user_name_text_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.photo_user_name_text_view))
            .check(ViewAssertions.matches(ViewMatchers.withText("Daniel Olah")))
    }
}