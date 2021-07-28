package com.example.trainingproject.models

import kotlin.collections.ArrayList

data class WalletCardResponse(
    val accounts : ArrayList<Account>
)

data class Account(
    val tokens: TokenDetail
)

data class TokenDetail(
    val tokenValue: String,
    val accountType: String,
    val tokenStatus: String,
    val last4 : String,
    val currency : String,
    val accountStatus: String,
    val kashKardType: String,
    val firstName: String,
    val lastName: String,
    val country: String,
    val cardType: String,
    val balance: Double,
    val defaultCard: Boolean,
)
