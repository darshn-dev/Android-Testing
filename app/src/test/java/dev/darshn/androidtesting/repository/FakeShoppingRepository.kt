package dev.darshn.androidtesting.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.darshn.androidtesting.data.local.ShoppingItem
import dev.darshn.androidtesting.data.remoteResponses.ImageResponse
import dev.darshn.androidtesting.util.Resource

class FakeShoppingRepository : ShoppingRepository{
    var shoppingItems = mutableListOf<ShoppingItem>()
    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrive = MutableLiveData<Float>()

    private var shouldReturnError = false

    fun setShouldNetworkError(value :Boolean){
        shouldReturnError = value
    }


    private  fun refreshLiveData(){
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrive.postValue(getTotalPrice())
    }

    private fun getTotalPrice() :Float{
        return shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
       shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllItem(): LiveData<List<ShoppingItem>> {
      return observableShoppingItems
    }

    override fun obserSum(): LiveData<Float> {
       return observableTotalPrive
    }

    override suspend fun searchImage(imageQuery: String): Resource<ImageResponse> {
            return if(shouldReturnError){
                Resource.error("Error",null)
            }else{
                Resource.success(ImageResponse(listOf(),0,0))
            }
    }
}