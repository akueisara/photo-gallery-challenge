package com.example.photogallerychallenge.ui.photos

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.example.photogallerychallenge.R
import com.example.photogallerychallenge.ServiceLocator
import com.example.photogallerychallenge.getFakeDatabasePhoto
import com.example.photogallerychallenge.repository.FakeRepository
import com.example.photogallerychallenge.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@ExperimentalCoroutinesApi
class PhotosFragmentTest {
    private lateinit var repository: Repository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initRepository() {
        repository = FakeRepository()
        val photo1 = getFakeDatabasePhoto("1yQEs9ZumxU", "Name1")
        val photo2 = getFakeDatabasePhoto("TYpX940GS_U")
        val photo3 = getFakeDatabasePhoto("3yQEs9ZumxU")
        (repository as FakeRepository).addDatabasePhotos(photo1, photo2, photo3)
        ServiceLocator.unsplashRepository = repository
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    // don't forget to turn animations off
    @Test
    fun clickPhoto_navigateToPhotoDetailFragment() {
        // GIVEN - On the home screen
        val scenario = launchFragmentInContainer<PhotosFragment>(Bundle(), R.style.AppTheme)

        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // WHEN - Click on the first list item
        onView(withId(R.id.photos_recycler_view))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText("Name1")), click()))

        // THEN - Verify that we navigate to the first detail screen
        verify(navController).navigate(
            PhotosFragmentDirections.actionPhotosToPhotoDetail( "1yQEs9ZumxU")
        )
    }
}