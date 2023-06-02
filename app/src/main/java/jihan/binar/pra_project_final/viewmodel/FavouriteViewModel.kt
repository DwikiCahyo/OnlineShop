package jihan.binar.pra_project_final.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jihan.binar.pra_project_final.model.favouriteproducts.FavouriteResponseItem
import jihan.binar.pra_project_final.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val apiService: ApiService):ViewModel() {

    //set up data
    private val _listFavourite = MutableLiveData<List<FavouriteResponseItem>>()
    val listFavourite: LiveData<List<FavouriteResponseItem>> = _listFavourite

    // get item favourite
    private val _itemFavourite = MutableLiveData<FavouriteResponseItem>()
    val itemFavourite: LiveData<FavouriteResponseItem> = _itemFavourite

    //set up loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    //get favourite product
    fun getFavouriteProducts(id:String){
        _isLoading.value = true
        apiService.getFavouriteProducts(id).enqueue(object :Callback<List<FavouriteResponseItem>>{
            override fun onResponse(
                call: Call<List<FavouriteResponseItem>>,
                response: Response<List<FavouriteResponseItem>>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _listFavourite.value = response.body()

                } else{
                    _isLoading.value = false
                    Log.e(TAG, "Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FavouriteResponseItem>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                _isLoading.value = false
            }

        })
    }

    fun postFavouriteProducts(id:String,name:String,productImage:String,price:Int,description:String){
        _isLoading.value = true
        apiService.addFavouriteProduct(id,name,productImage,price,description).enqueue(object :Callback<FavouriteResponseItem>{
            override fun onResponse(
                call: Call<FavouriteResponseItem>,
                response: Response<FavouriteResponseItem>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _itemFavourite.value = response.body()

                } else{
                    _isLoading.value = false
                    Log.e(TAG, "Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FavouriteResponseItem>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                _isLoading.value = false
            }
        })
    }

    fun deleteFavouriteProducts(userId:String,favId:String){
        _isLoading.value = true
        apiService.deleteFavouriteProduct(userId,favId).enqueue(object :Callback<FavouriteResponseItem>{
            override fun onResponse(
                call: Call<FavouriteResponseItem>,
                response: Response<FavouriteResponseItem>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _itemFavourite.value = response.body()

                } else{
                    _isLoading.value = false
                    Log.e(TAG, "Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FavouriteResponseItem>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                _isLoading.value = false
            }
        })
    }

     companion object{
         const val TAG = "FavouriteViewModel"
     }
}