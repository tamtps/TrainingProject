package com.example.trainingproject.viewmodels

import android.content.Context
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseViewModel
import com.example.trainingproject.models.*
import retrofit2.Call


class CouponCardViewModel : BaseViewModel<Response<Coupon>, CouponCardViewModel>(CouponCardViewModel::class.java) {
    lateinit var token: String
    lateinit var fkUser : String
    lateinit var keyword: String
    lateinit var filter: String
    lateinit var pageIndex: String
    lateinit var pageSize: String
    lateinit var context: Context

    fun init(token: String,
             deviceId :String,
             fkUser : String,
             keyword: String,
             filter: String,
             pageIndex: String,
             pageSize: String,
             context: Context
    ){
        this.token = token
        this.fkUser = fkUser
        this.keyword = keyword
        this.filter = filter
        this.pageIndex = pageIndex
        this.pageSize = pageSize
        this.context = context
    }

    override fun retrofitCall(): Call<Response<Coupon>> {
        return RetrofitClient().instance.getCoupon(token, fkUser, keyword, filter, pageIndex, pageSize)
    }

}