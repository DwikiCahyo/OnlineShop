package jihan.binar.pra_project_final.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jihan.binar.pra_project_final.model.news.ResponseNewsItem
import jihan.binar.pra_project_final.model.products.DetailProductResponse
import jihan.binar.pra_project_final.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailNewsViewModel @Inject constructor(private val api: ApiService): ViewModel() {

    //get data news update detail
    private val _itemNewsDetail = MutableLiveData<ResponseNewsItem>()
    val itemNewsDetail: LiveData<ResponseNewsItem> = _itemNewsDetail


    //set up loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailNewsUpdate(newsId:String){
        _isLoading.value = true
        api.getNewsUpdateDetail(newsId).enqueue(object : Callback<ResponseNewsItem> {
            override fun onResponse(
                call: Call<ResponseNewsItem>,
                response: Response<ResponseNewsItem>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _itemNewsDetail.value = response.body()

                } else{
                    _isLoading.value = false
                    Log.e(TAG, "Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseNewsItem>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Error ${t.message}")
            }

        })
    }


    companion object{
        private const val TAG = "DetailViewModel"
    }
}