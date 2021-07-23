package com.example.trainingproject.api

import com.example.trainingproject.models.CountryResponse
import com.example.trainingproject.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("accounts/login/app")
    fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("deviceId") deviceId: String,
        @Field("deviceToken") deviceToken: String,
        @Field("appPlatform") appPlatform: String,
        @Field("keyStore") keyStore: String,
        //@Field("osversion") osversion: String,
        //@Field("appVersion") appVersion: String,
    ) : Call<LoginResponse>

    @GET("countries")
    fun getCountries() : Call<CountryResponse>
}