package jihan.binar.pra_project_final.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jihan.binar.pra_project_final.model.products.DetailProductResponse
import jihan.binar.pra_project_final.model.products.ProductResponseItem
import jihan.binar.pra_project_final.model.slider.SliderResponseItem
import jihan.binar.pra_project_final.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val api:ApiService):ViewModel() {

    //get data new product detail
    private val _itemNewDetail = MutableLiveData<DetailProductResponse>()
    val itemNewDetail: LiveData<DetailProductResponse> = _itemNewDetail

    //get data second product detail
    private val _itemSecondDetail = MutableLiveData<DetailProductResponse>()
    val itemSecondDetail: LiveData<DetailProductResponse> = _itemSecondDetail

    //set up loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailNewProduct(productId:String){
        _isLoading.value = true
        api.getNewProductsDetail(productId).enqueue(object :Callback<DetailProductResponse>{
            override fun onResponse(
                call: Call<DetailProductResponse>,
                response: Response<DetailProductResponse>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _itemNewDetail.value = response.body()

                } else{
                    _isLoading.value = false
                    Log.e(TAG, "Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailProductResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Error ${t.message}")
            }

        })
    }

    fun getDetailSecondProduct(productId:String){
        _isLoading.value = true
        api.getSecondProductsDetail(productId).enqueue(object :Callback<DetailProductResponse>{
            override fun onResponse(
                call: Call<DetailProductResponse>,
                response: Response<DetailProductResponse>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _itemSecondDetail.value = response.body()

                } else{
                    _isLoading.value = false
                    Log.e(TAG, "Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailProductResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Error ${t.message}")
            }

        })
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }
}