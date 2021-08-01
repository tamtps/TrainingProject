package com.example.trainingproject.models
//MODEL
class Response<T>(
     var clientMessage: String,
     var countRecord: String,
     var statusCode: String,
     var status: Int,
     var result: ArrayList<T>
)


data class Point(
    val totalPoint: Long,
    val currentPoint: Long,
    val levelUser: Int
)

data class Country(
    val name: String,
    val alpha2Code: String,
    val callingCodes: String,
    val flag: String,
    val regex: String,
    var favorite: Boolean,
)

data class Coupon(
    val merchantName: String,
    val expireDate: String,
    val imageShow: String,
    val percentOff: Int
)

///////////////////////////////////////
data class LoginResponse(
    var clientMessage: String,
    var countRecord: String,
    var statusCode: String,
    var status: Int,
    var result: LoginResult,
    var trace: Trace
)

data class LoginResult(
    val accountInfo: AccountInfo,
    val loginInformation: LoginInformation
)

data class AccountInfo(
    val idUsers: String,
    val fname: String,
    val lname: String,
    val flag: String,
    val username: String,
    val email: String,
    val numberPoint: Int,
)

data class LoginInformation(
    val token: String,
    val loginExpiry: String
)

data class Trace(
    val appVersion: String
)


//////////////////////////////////////
data class GiftCardResponse(
    val result: TransactionDisplay
)

data class TransactionDisplay(
    val transDisplay: ArrayList<TransactionDetail>
)

data class TransactionDetail(
    val idTransaction: Int,
    val cert_value: String,
    val imgCard: String,
    val imgThumbnail: String,
    val currency: String,
    val merchantName: String,
    val cardNameReceiver: String,
    val cardNameSender: String,
    val cardNum: String,
)

//////////////////////////////
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

/////////////////////////////////
data class VideoResponse(
    var clientMessage: String,
    var countRecord: String,
    var statusCode: String,
    var status: Int,
    var result: Content,
)

data class Content(
    var content: ArrayList<Videos>
)

data class Videos(
    val key: String,
    val value: String
)
