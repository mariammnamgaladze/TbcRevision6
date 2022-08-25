package com.example.tbcrevision6.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tbcrevision6.data.DataStoreHandler
import com.example.tbcrevision6.model.LoginForm
import com.example.tbcrevision6.model.UserInfo
import com.example.tbcrevision6.network.RetrofitInstance
import com.example.tbcrevision6.utils.ResponseHandler
import kotlinx.coroutines.flow.flow

class LoginViewModel: ViewModel() {

    fun getLoginFlow(userInfo: UserInfo) = flow<ResponseHandler> {
        emit(ResponseHandler.Loader(isLoading = true))
        val response = RetrofitInstance.getAuthApi().getLoginForm(userInfo)
        if (response.isSuccessful && response.body() != null) {
            emit(ResponseHandler.Success<LoginForm>(response.body()!!))
        } else {
            emit(ResponseHandler.Error(response.errorBody()?.string() ?: "Error!"))
        }
        emit(ResponseHandler.Loader(isLoading = false))
    }

    suspend fun save(key: String, value: String) {
        DataStoreHandler.save(key, value)
    }

    fun getPreferences() = DataStoreHandler.getPreferences()

}