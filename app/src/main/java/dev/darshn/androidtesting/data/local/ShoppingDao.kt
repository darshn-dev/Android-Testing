package dev.darshn.androidtesting.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun delete(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM ShoppingItem")
    fun observeShoppingItem():LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(price * amount) from shoppingitem")
    fun observeTotalPrice():LiveData<Float>
}