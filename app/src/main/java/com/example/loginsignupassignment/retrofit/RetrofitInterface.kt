package com.example.loginsignupassignment.retrofit

import com.example.loginsignupassignment.model.LoginResponseModel
import com.example.loginsignupassignment.model.SignUpResponseModel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitInterface {

    @FormUrlEncoded
    @POST("/api/signup")
    suspend fun signUpNewUser(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("country") country: String,
        @Field("city") city: String
    ) : Response<SignUpResponseModel>

    @FormUrlEncoded
    @POST("/api/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<LoginResponseModel>
}