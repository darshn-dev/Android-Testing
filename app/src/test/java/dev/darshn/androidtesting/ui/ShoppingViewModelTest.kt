package dev.darshn.androidtesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import dev.darshn.androidtesting.MainCoroutineRule
import dev.darshn.androidtesting.getOrAwaitValueTest

import dev.darshn.androidtesting.repository.FakeShoppingRepository
import dev.darshn.androidtesting.util.Constants
import dev.darshn.androidtesting.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ShoppingViewModelTest{

    private lateinit var viewModel: ShoppingViewModel

    @get:Rule
    var instantTaskExecutorRule= InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup(){
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty return error`(){
        viewModel.insertShoppingItme("name","","3.0")
        val value = viewModel.inserShoppingItem.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with long name return error`(){
        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH+1){
                append(1)
            }
        }
        viewModel.insertShoppingItme(string,"5","3.0")
        val value = viewModel.inserShoppingItem.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }



    @Test
    fun `insert shopping item with long price return error`(){
        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH+1){
                append(1)
            }
        }
        viewModel.insertShoppingItme("Mname ","5",string)
        val value = viewModel.inserShoppingItem.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }



    @Test
    fun `insert shopping item with hight amoubt return error`(){

        viewModel.insertShoppingItme("Mname ","9999989999999998888888888888888888888999","3.0")
        val value = viewModel.inserShoppingItem.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid value amoubt return success`(){

        viewModel.insertShoppingItme("Name ","8","3.0")
        val value = viewModel.inserShoppingItem.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }


}