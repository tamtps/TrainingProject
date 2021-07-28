package com.example.trainingproject.models

import kotlin.collections.ArrayList

data class GiftCardResponse(
    val countRecord: Int,
    val statusCode: String,
    val status: String,
    val result: TransactionDisplay
)

data class TransactionDisplay(
    val transDisplay: ArrayList<TransactionDetail>
)

data class TransactionDetail(
    val idTransaction: Int,
    val cert_value: String,
    val imgCard: String,
    val imgThumbnail : String,
    val currency : String,
    val merchantName: String,
    val cardNameReceiver: String,
    val cardNameSender: String,
    val cardNum: String,
)
