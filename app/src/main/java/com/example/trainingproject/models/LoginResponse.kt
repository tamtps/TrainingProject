package com.example.trainingproject.models

import com.example.trainingproject.models.User
import com.google.gson.JsonObject
import java.util.*

data class LoginResponse(
    val clientMessage: String,
    val countRecord: Int,
    val statusCode: String,
    val status: Int,
    val message: String,
    val result: Result
)
data class Result(
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