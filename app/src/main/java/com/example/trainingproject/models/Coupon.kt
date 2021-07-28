package com.example.trainingproject.models

data class Coupon(
    val merchantName : String,
    val expireDate : String,
    val imageShow : String,
    val percentOff : Int
    )

data class CouponResponse(
    val statusCode : String,
    val status : Int,
    val result: List<Coupon>
)