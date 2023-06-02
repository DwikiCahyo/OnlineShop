package jihan.binar.pra_project_final.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jihan.binar.pra_project_final.model.cart.CartResponse
import jihan.binar.pra_project_final.model.cart.CartResponseItem
import jihan.binar.pra_project_final.model.favouriteproducts.FavouriteResponseItem
import jihan.binar.pra_project_final.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sharedPreferences: SharedPreferences
    ): ViewModel() {

    //set up data
    private val _listCart = MutableLiveData<List<CartResponseItem>>()
    val listCart: LiveData<List<CartResponseItem>> = _listCart

    //item cart
    private val _itemCart = MutableLiveData<CartResponseItem>()
    val itemCart:LiveData<CartResponseItem> = _itemCart




    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val id = sharedPreferences.getString("id","Id Kosong")

    init {
        getCartFavorite(id!!)
    }


    fun getCartFavorite(id:String){
        _isLoading.value = true
        apiService.getCart(id).enqueue(object :Callback<List<CartResponseItem>>{
            override fun onResponse(
                call: Call<List<CartResponseItem>>,
                response: Response<List<CartResponseItem>>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _listCart.value = response.body()

                } else{
                    _isLoading.value = false
                    Log.e(FavouriteViewModel.TAG, "Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<CartResponseItem>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                _isLoading.value = false
            }

        })
    }

    fun addCart(id:String,name:String,productImage:String,price:Int,desc:String){
        _isLoading.value = true
        apiService.addCart(id,name,productImage,price,desc).enqueue(object : Callback<CartResponseItem>{
            override fun onResponse(
                call: Call<CartResponseItem>,
                response: Response<CartResponseItem>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _itemCart.value = response.body()

                } else{
                    _isLoading.value = false
                    Log.e(TAG, "Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CartResponseItem>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                _isLoading.value = false
            }

        })

    }
    companion object{
        const val TAG = "CartViewModel"
    }
}