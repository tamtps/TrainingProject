package com.example.trainingproject.models

data class LoginResponse(
    val clientMessage: String,
    val countRecord: Int,
    val statusCode: String,
    val status: Int,
    val message: String,
    val result: LoginResult,
    val trace : Trace
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
)

data class Trace(
    val appVersion : String
)