package jihan.binar.pra_project_final.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jihan.binar.pra_project_final.model.user.UserResponseItem
import jihan.binar.pra_project_final.network.ApiClient
import jihan.binar.pra_project_final.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(var api : ApiService) : ViewModel() {

    private val apiService: ApiService = ApiClient.provideUserApi(ApiClient.provideRetrofit())

    private val _registrationResult = MutableLiveData<Result>()
    val registrationResult: LiveData<Result> = _registrationResult

    data class Result(val success: Boolean, val errorMessage: String?)

    fun registerUser(username: String, password: String, email: String) {
        val user = UserResponseItem(username = username, password = password, email = email)

        val call = apiService.registerUser(user)
        call.enqueue(object : Callback<UserResponseItem> {
            override fun onResponse(
                call: Call<UserResponseItem>,
                response: Response<UserResponseItem>
            ) {
                if (response.isSuccessful) {
                    _registrationResult.value = Result(success = true, errorMessage = null)
                    val registeredUser = response.body()

                } else {
                    val errorMessage = response.errorBody()?.string()
                    _registrationResult.value = Result(success = false, errorMessage = errorMessage)

                }
            }

            override fun onFailure(call: Call<UserResponseItem>, t: Throwable) {
                _registrationResult.value = Result(success = false, errorMessage = t.message)

            }
        })
    }
}