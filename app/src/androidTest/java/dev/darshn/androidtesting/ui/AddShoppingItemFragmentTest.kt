package dev.darshn.androidtesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.d.AddShoppingItemFragment
import dev.darshn.androidtesting.R
import dev.darshn.androidtesting.data.local.ShoppingItem
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

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class AddShoppingItemFragmentTest{

    @get :Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Inject
    lateinit var fragmentfactoryFactory: ShoppingFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }


    @Test
    fun pressBackButton_popBackstack(){
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(),navController)
        }


        pressBack()
        verify(navController).popBackStack()


    }


    @Test
    fun clickInsertDB_itemInsert(){
        val testViewModel = ShoppingViewModel(FakeShoppingAndroidRepository())
        launchFragmentInHiltContainer<AddShoppingItemFragment> (
            fragmentFactory = fragmentfactoryFactory
        ){
            viewModel = testViewModel
        }

        onView(withId(R.id.etShoppingItemName)).perform(replaceText("item"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("30"))

        onView(withId(R.id.btnAddShoppingItem)).perform(click())

       assertThat(testViewModel.shoppingItems.getOrAwaitValue()).contains(ShoppingItem("item",30,5f,"") )

    }


}