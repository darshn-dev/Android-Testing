package dev.darshn.androidtesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.darshn.androidtesting.R
import dev.darshn.androidtesting.adapters.ShoppingItemAdapter
import dev.darshn.androidtesting.data.local.ShoppingItem
import dev.darshn.androidtesting.getOrAwaitValue
import dev.darshn.androidtesting.launchFragmentInHiltContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingFragmentTest{
    @get:Rule
    var hiltrule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        hiltrule.inject()
    }

    @Inject
    lateinit var testFragmentFactory: TestShoppingFragmentFactory

    @Test
    fun swipeItem_deletefromDB(){
        val item = ShoppingItem("name",1,1f,"url",5)
        var testViewModel : ShoppingViewModel?=null
        launchFragmentInHiltContainer<ShoppingFragment>(
            fragmentFactory = testFragmentFactory
        ) {
            testViewModel = viewModel
            viewModel?.insertItem(item)
        }

        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ShoppingItemViewHolder>(
                0,
                swipeLeft()
            )
        )

        assertThat(testViewModel?.shoppingItems?.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun clickAddShoppingItemButtom_gotoAddShoppingFragment(){
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<ShoppingFragment>{
            Navigation.setViewNavController(requireView(),navController)
        }
        onView(withId(R.id.fabAddShoppingItem)).perform(click())
        verify(navController).navigate(
            R.id.action_shoppingFragment_to_addShoppingItemFragment
        )
    }


}