package dev.darshn.androidtesting.repository

import androidx.lifecycle.LiveData
import dev.darshn.androidtesting.data.local.ShoppingDao
import dev.darshn.androidtesting.data.local.ShoppingItem
import dev.darshn.androidtesting.data.remoteResponses.ImageResponse
import dev.darshn.androidtesting.data.remoteResponses.PixabayAPI
import dev.darshn.androidtesting.util.Resource
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor (
    private val shoppingDao: ShoppingDao,
    private  val pixabayAPI: PixabayAPI
) :ShoppingRepository{

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
       shoppingDao.insertItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.delete(shoppingItem)
    }

    override fun observeAllItem(): LiveData<List<ShoppingItem>> {
      return shoppingDao.observeShoppingItem()
    }

    override fun obserSum(): LiveData<Float> {
       return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchImage(imageQuery)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("Unknown error", null)
            }else{
                Resource.error("Unknown error",null)
            }
        }catch (e:Exception){
            Resource.error("Couldn't reach server", null)
        }
    }

}