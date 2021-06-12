package dev.darshn.androidtesting.repository

import android.media.Image
import androidx.lifecycle.LiveData
import dev.darshn.androidtesting.data.local.ShoppingItem
import dev.darshn.androidtesting.data.remoteResponses.ImageResponse
import dev.darshn.androidtesting.util.Resource
import retrofit2.Response

interface ShoppingRepository {
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllItem(): LiveData<List<ShoppingItem>>

    fun obserSum():LiveData<Float>

    suspend fun searchImage(imageQuery:String) :Resource<ImageResponse>
}