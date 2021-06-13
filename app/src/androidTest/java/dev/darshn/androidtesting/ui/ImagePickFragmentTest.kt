package dev.darshn.androidtesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions

import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.darshn.androidtesting.R
import dev.darshn.androidtesting.adapters.ImageAdapter
import dev.darshn.androidtesting.getOrAwaitValue
import dev.darshn.androidtesting.launchFragmentInHiltContainer
import dev.darshn.androidtesting.repository.FakeShoppingAndroidRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ImagePickFragmentTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun clickImage_PopBackstackSetImageUrl(){
        val navController = mock(NavController::class.java)
        val imageUrl = "TEST"

        val testViewModel = ShoppingViewModel(FakeShoppingAndroidRepository())
        launchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory ) {
            Navigation.setViewNavController(requireView(),navController)
            imagAdapter.images = listOf(imageUrl)
            viewModel=testViewModel
        }

        onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(
                0,
                click()
            )
        )

        verify(navController).popBackStack()
        assertThat(testViewModel.curImage.getOrAwaitValue()).isEqualTo(imageUrl)
    }
}