package com.example.trainingproject.viewmodels

import android.content.Context
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseViewModel
import com.example.trainingproject.models.*
import retrofit2.Call


class CouponCardViewModel : BaseViewModel<Response<ArrayList<Coupon>>, CouponCardViewModel>(CouponCardViewModel::class.java) {
    lateinit var fkUser : String
    lateinit var keyword: String
    lateinit var filter: String
    lateinit var pageIndex: String
    lateinit var pageSize: String

    fun init(
             fkUser : String,
             keyword: String,
             filter: String,
             pageIndex: String,
             pageSize: String,
    ){
        this.fkUser = fkUser
        this.keyword = keyword
        this.filter = filter
        this.pageIndex = pageIndex
        this.pageSize = pageSize
    }

    override fun retrofitCall(): Call<Response<ArrayList<Coupon>>> {
        return RetrofitClient().instance.getCoupon(fkUser, keyword, filter, pageIndex, pageSize)
    }

}