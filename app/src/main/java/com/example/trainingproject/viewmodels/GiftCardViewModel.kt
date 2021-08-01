package com.example.trainingproject.viewmodels

import android.content.Context
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseViewModel
import com.example.trainingproject.models.*
import retrofit2.Call


class GiftCardViewModel :
    BaseViewModel<GiftCardResponse, GiftCardViewModel>(GiftCardViewModel::class.java) {

    lateinit var token: String
    lateinit var receiverUserId: String
    lateinit var pageIndex: String
    lateinit var pageSize: String
    lateinit var filter: String
    lateinit var keyword: String

    fun init(
        token: String,
        receiverUserId: String,
        keyword: String,
        pageIndex: String,
        pageSize: String,
        filter: String,
    ) {
        this.token = token
        this.keyword = keyword
        this.receiverUserId = receiverUserId
        this.pageIndex = pageIndex
        this.pageSize = pageSize
        this.filter = filter
    }

    override fun retrofitCall(): Call<GiftCardResponse> {
        return RetrofitClient().instance.getGiftCard(
            token,
            receiverUserId,
            keyword,
            pageIndex,
            pageSize,
            filter
        )
    }
}