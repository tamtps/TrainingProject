package com.example.trainingproject.models

data class LoginResponse(
    val clientMessage: String,
    val countRecord: Int,
    val statusCode: String,
    val status: Int,
    val message: String,
    val result: LoginResult
)

data class LoginResult(
    val accountInfo: AccountInfo,
    val loginInformation: LoginInformation
)

data class AccountInfo(
    val idUsers: String,
    val fname: String,
    val lname: String,
    val username: String,
    val email: String,
)

data class LoginInformation(
    val token: String,
)