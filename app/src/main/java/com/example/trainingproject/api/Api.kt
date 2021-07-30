package com.example.trainingproject.api

import com.example.trainingproject.models.*
import retrofit2.Call
import retrofit2.http.*

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

    @FormUrlEncoded
    @POST("api/27/getAccounts")
    fun getWalletCard(
        @Header("token") token: String,
        @Field("accountSpecification") accountSpecification: String,
        @Field("action") action: String,
        @Field("storeId") storeId: String,
        @Field("excludeCards") excludeCards: String,
    ) : Call<WalletCardResponse>

    @FormUrlEncoded
    @POST("deals/getWalletDisplayByReceiverUserId")
    fun getGiftCard(
        @Header("token") token: String,
        @Field("receiverUserId") receiverUserId: String,
        @Field("keyword") keyword: String,
        @Field("pageIndex") pageIndex: String,
        @Field("pageSize") pageSize: String,
        @Field("filter") filter: String,
    ) : Call<GiftCardResponse>

    @GET("api/support?category=1")
    fun getVideo(
        @Header("token") token: String
    ): Call<VideoResponse>

    @GET("point/get/point/level/167")
    fun getPoint(
        @Header("token") token: String
    ): Call<PointResponse>

    @FormUrlEncoded
    @POST("special/campaign/coupon/wallet/user/all")
    fun getCoupon(
        @Header("token") token: String,
        @Field("fkUser") fkUser: String,
        @Field("keyword") keyword: String?,
        @Field("filter") filter: String,
        @Field("pageIndex") pageIndex: String,
        @Field("pageSize") pageSize: String,
    ): Call<CouponResponse>
}