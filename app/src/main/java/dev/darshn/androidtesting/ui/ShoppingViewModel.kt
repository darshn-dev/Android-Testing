package dev.darshn.androidtesting.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.darshn.androidtesting.data.local.ShoppingItem
import dev.darshn.androidtesting.data.remoteResponses.ImageResponse
import dev.darshn.androidtesting.repository.ShoppingRepository
import dev.darshn.androidtesting.util.Constants
import dev.darshn.androidtesting.util.Event
import dev.darshn.androidtesting.util.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class ShoppingViewModel @ViewModelInject constructor(
    private val  repository: ShoppingRepository
) : ViewModel() {
    val shoppingItems = repository.observeAllItem()

    val totalPrice = repository.obserSum()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImage: LiveData<String> = _curImageUrl

     val _inserShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val inserShoppingItem:LiveData<Event<Resource<ShoppingItem>>> = _inserShoppingItemStatus

    fun setCurImageUrl(url :String){
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem){
        viewModelScope.launch {
            repository.deleteShoppingItem(shoppingItem)
        }
    }

    fun insertItem(shoppingItem: ShoppingItem){
        viewModelScope.launch {
            repository.insertShoppingItem(shoppingItem)
        }
    }

    fun insertShoppingItme(name:String, amount:String, price:String){
            if(name.isEmpty() || amount.isEmpty() || price.isEmpty()){
                _inserShoppingItemStatus.postValue(Event(Resource.error("Should not be empty", null)))
                return
            }

        if(name.length> Constants.MAX_NAME_LENGTH){
            _inserShoppingItemStatus.postValue(Event(Resource.error("Name is too big", null)))
            return
        }

        if(price.length> Constants.MAX_PRICE_LENGTH){
            _inserShoppingItemStatus.postValue(Event(Resource.error("Price is too big", null)))
            return
        }

       val amt = try {
           amount.toInt()
       }catch (e: Exception){
           _inserShoppingItemStatus.postValue(Event(Resource.error("Please enter valid amount", null)))
           return
       }

        val shoppintItem = ShoppingItem(name, amt, price.toFloat(), _curImageUrl.value?: "")
        insertItem(shoppintItem)
        setCurImageUrl("")
        _inserShoppingItemStatus.postValue(Event(Resource.success(shoppintItem)))
    }

    fun searchForImage(img:String){
        if(img.isEmpty()){
            return
        }

        _images.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.searchImage(img)
            _images.value = Event(response)
        }
    }
}