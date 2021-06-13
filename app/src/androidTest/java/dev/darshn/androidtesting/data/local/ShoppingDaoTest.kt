package dev.darshn.androidtesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest

import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.darshn.androidtesting.getOrAwaitValue


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hilt = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database:ShoppingDB

    lateinit var dao :ShoppingDao

    @get:Rule
    var instanceTaskExecutorRule= InstantTaskExecutorRule()


    @Before
    fun setup(){
            hilt.inject()
           dao = database.shoppingDao()
    }

    @After
    fun tearDown(){
        database.close()
    }


    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name",1,1f,"url", id=1)
        dao.insertItem(shoppingItem )

        //It will return a live data, but we dont need it, becoz we are testing in main thread
        val allShopItem = dao.observeShoppingItem().getOrAwaitValue()
        assertThat(allShopItem).contains(shoppingItem)
    }


    @Test
    fun deleteShoppintItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name",1,1f,"url", id=1)
        dao.insertItem(shoppingItem )
        dao.delete(shoppingItem)
        val allShopItem = dao.observeShoppingItem().getOrAwaitValue()
        assertThat(allShopItem).doesNotContain(allShopItem)
    }

    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        var shoppingItem = ShoppingItem("name",1,10f,"url", id=1)
        dao.insertItem(shoppingItem )
        shoppingItem = ShoppingItem("name",2,20f,"url", id=2)
        dao.insertItem(shoppingItem )
        shoppingItem = ShoppingItem("name",0,100f,"url", id=3)
        dao.insertItem(shoppingItem )

        val totalPricesum = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPricesum).isEqualTo(1*10f + 2*20f)
    }
}