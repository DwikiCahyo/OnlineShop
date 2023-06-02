package jihan.binar.pra_project_final.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jihan.binar.pra_project_final.model.news.ResponseNewsItem
import jihan.binar.pra_project_final.model.products.ProductResponseItem
import jihan.binar.pra_project_final.model.slider.SliderResponseItem
import jihan.binar.pra_project_final.model.user.UserResponseItem
import jihan.binar.pra_project_final.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService): ViewModel() {

    //get data item slider
    private val _itemSlider = MutableLiveData<List<SliderResponseItem>>()
    val itemSlider: LiveData<List<SliderResponseItem>> = _itemSlider

    //get data news
    private val _itemNews = MutableLiveData<List<ResponseNewsItem>>()
    val itemNews: MutableLiveData<List<ResponseNewsItem>> = _itemNews

    //get data product new
    private val _itemProduct = MutableLiveData<List<ProductResponseItem>>()
    val itemProduct: LiveData<List<ProductResponseItem>> = _itemProduct

    //get data productSecond
    private val _itemProductSecond = MutableLiveData<List<ProductResponseItem>>()
    val itemProductSecond: LiveData<List<ProductResponseItem>> = _itemProductSecond


    //set up loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    //setup loading for second product
    private val _isLoadingSecond = MutableLiveData<Boolean>()
    val isLoadingSecond: LiveData<Boolean> = _isLoadingSecond

    //get slider
    fun getSliderItem(){
        _isLoading.value = true
        apiService.getSliders().enqueue(object :Callback<List<SliderResponseItem>>{
            override fun onResponse(
                call: Call<List<SliderResponseItem>>,
                response: Response<List<SliderResponseItem>>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _itemSlider.value = response.body()

                } else{
                    _isLoading.value = false
                    Log.e(TAG, "Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<SliderResponseItem>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                _isLoading.value = false
            }

        })
    }

    //get product
    fun getNews(){
        _isLoading.value = true
        apiService.getNews().enqueue(object : Callback<List<ResponseNewsItem>>{
            override fun onResponse(
                call: Call<List<ResponseNewsItem>>,
                response: Response<List<ResponseNewsItem>>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _itemNews.value = response.body()

                } else{
                    _isLoading.value = false
                    Log.e(TAG, "Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ResponseNewsItem>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                _isLoading.value = false
            }

        })
    }

    //get product
    fun getProduct(id:String){
        _isLoading.value = true
        apiService.getProducts(id).enqueue(object : Callback<List<ProductResponseItem>>{
            override fun onResponse(
                call: Call<List<ProductResponseItem>>,
                response: Response<List<ProductResponseItem>>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _itemProduct.value = response.body()

                } else{
                    _isLoading.value = false
                    Log.e(TAG, "Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ProductResponseItem>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                _isLoading.value = false
            }

        })
    }

    fun getProductSecond(id:String){
        _isLoadingSecond.value = true
        apiService.getProducts(id).enqueue(object :Callback<List<ProductResponseItem>>{
            override fun onResponse(
                call: Call<List<ProductResponseItem>>,
                response: Response<List<ProductResponseItem>>
            ) {
                if (response.isSuccessful){
                    _isLoadingSecond.value = false
                    _itemProductSecond.value = response.body()

                } else{
                    _isLoadingSecond.value = false
                    Log.e(TAG, "Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ProductResponseItem>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                _isLoading.value = false
            }

        })
    }

    companion object{
        private const val TAG = "HomeViewModel"
    }
}