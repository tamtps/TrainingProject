package com.example.trainingproject.viewmodels

import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseViewModel
import com.example.trainingproject.models.WalletCardResponse
import retrofit2.Call


class WalletCardViewModel : BaseViewModel<WalletCardResponse, WalletCardViewModel>(WalletCardViewModel::class.java) {
    lateinit var accountSpecification : String
    lateinit var action: String
    lateinit var storeId: String
    lateinit var excludeCards: String

    fun init(
             accountSpecification : String,
             action: String, storeId: String,
             excludeCards: String){
        this.accountSpecification = accountSpecification
        this.action = action
        this.storeId = storeId
        this.excludeCards = excludeCards
    }

    override fun retrofitCall(): Call<WalletCardResponse> {
        return RetrofitClient().walletCardInstance.getWalletCard(accountSpecification, action, storeId, excludeCards)
    }


}