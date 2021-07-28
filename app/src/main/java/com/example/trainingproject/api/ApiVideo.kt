package com.example.trainingproject.api

import com.example.trainingproject.models.CouponResponse
import com.example.trainingproject.models.Point
import com.example.trainingproject.models.VideoResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiVideo {
    @GET("api/support?category=1")
    fun getVideo(
        @Header("token") token: String
    ): Call<VideoResponse>

    @GET("point/get/point/level/167")
    fun getPoint(
        @Header("token") token: String
    ): Call<Point>

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