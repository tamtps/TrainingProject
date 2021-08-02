package com.example.trainingproject.viewmodels

import android.content.Context
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseViewModel
import com.example.trainingproject.models.*
import retrofit2.Call


class GiftCardViewModel :
    BaseViewModel<Response<TransactionDisplay>, GiftCardViewModel>(GiftCardViewModel::class.java) {

    lateinit var receiverUserId: String
    lateinit var pageIndex: String
    lateinit var pageSize: String
    lateinit var filter: String
    lateinit var keyword: String

    fun init(
        receiverUserId: String,
        keyword: String,
        pageIndex: String,
        pageSize: String,
        filter: String,
    ) {
        this.keyword = keyword
        this.receiverUserId = receiverUserId
        this.pageIndex = pageIndex
        this.pageSize = pageSize
        this.filter = filter
    }

    override fun retrofitCall(): Call<Response<TransactionDisplay>> {
        return RetrofitClient().instance.getGiftCard(
            receiverUserId,
            keyword,
            pageIndex,
            pageSize,
            filter
        )
    }
}