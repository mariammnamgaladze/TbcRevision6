package com.example.tbcrevision6.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tbcrevision6.model.RegisterForm
import com.example.tbcrevision6.model.UserInfo
import com.example.tbcrevision6.network.RetrofitInstance
import com.example.tbcrevision6.utils.ResponseHandler
import kotlinx.coroutines.flow.flow

class RegisterViewModel: ViewModel() {

    fun getRegisterFlow(userInfo: UserInfo) = flow<ResponseHandler> {
        emit(ResponseHandler.Loader(isLoading = true))
        val response = RetrofitInstance.getAuthApi().getRegisterForm(userInfo)
        if (response.isSuccessful && response.body() != null) {
            emit(ResponseHandler.Success<RegisterForm>(response.body()!!))
        } else {
            emit(ResponseHandler.Error(response.errorBody()?.string() ?: "Error!"))
        }
        emit(ResponseHandler.Loader(isLoading = false))
    }

}