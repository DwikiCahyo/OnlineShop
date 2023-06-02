package jihan.binar.pra_project_final.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import jihan.binar.pra_project_final.model.datastore.PreferenceManager
import jihan.binar.pra_project_final.model.user.ResponseUser
import jihan.binar.pra_project_final.model.user.ResponseUserItem
import jihan.binar.pra_project_final.model.user.UserResponse
import jihan.binar.pra_project_final.model.user.UserResponseItem
import jihan.binar.pra_project_final.network.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: ApiService,
    private val prefManager:PreferenceManager,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    //get data item user
    private val _userLogin = MutableLiveData<List<UserResponseItem>>()
    val userLogin: LiveData<List<UserResponseItem>> = _userLogin

    //get only one user
    private val _userItemLogin = MutableLiveData<UserResponseItem>()
    val userItemLogin:LiveData<UserResponseItem> = _userItemLogin

    //set up loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getUserLoginItem(id:String){
        apiService.getUserItem(id).enqueue(object : Callback<UserResponseItem>{
            override fun onResponse(
                call: Call<UserResponseItem>,
                response: Response<UserResponseItem>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _userItemLogin.value = response.body()
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponseItem>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message.toString())
            }

        })

    }


    fun getUserLogin(){
        apiService.getUser().enqueue(object : Callback<List<UserResponseItem>>{
            override fun onResponse(
                call: Call<List<UserResponseItem>>,
                response: Response<List<UserResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _userLogin.value = response.body()
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message.toString())
            }

        })
    }

    //get login state
    fun getLoginState(): LiveData<Boolean> {
        return prefManager.getLoginState().asLiveData()
    }

    //save login state
    fun saveLoginState(state:Boolean){
        viewModelScope.launch {
            prefManager.saveLoginState(state)
        }
    }

    //save id
    fun saveIdPreferences(key: String,value: String){
        val editor = sharedPreferences.edit()
        editor.putString(key,value)
        editor.apply()
    }

    //get id
    fun getIdPreferences(key: String):String?{
        return sharedPreferences.getString(key,"Id Kosong")
    }

    //logout
    fun logout(){
        viewModelScope.launch {
            prefManager.logout()
        }
    }

    fun logoutPref(){
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object{
        private const val TAG = "LoginViewModel"
    }

}