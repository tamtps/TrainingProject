package com.example.trainingproject.api

import com.example.trainingproject.models.GiftCardResponse
import com.example.trainingproject.models.WalletCardResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiCard {
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
}