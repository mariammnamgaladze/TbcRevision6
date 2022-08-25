package com.example.tbcrevision6.network

import com.example.tbcrevision6.model.LoginForm
import com.example.tbcrevision6.model.RegisterForm
import com.example.tbcrevision6.model.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun getLoginForm(@Body userInfo: UserInfo): Response<LoginForm>

    @POST("register")
    suspend fun getRegisterForm(@Body userInfo: UserInfo): Response<RegisterForm>

}