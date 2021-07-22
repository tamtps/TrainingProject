package com.example.trainingproject.models

data class User(
    val userId: String,
    val fullname: String,
    val deviceId: String,
    val deviceToken: String,
    val appPlatform: String,
    val keyStore: String,
    val osversion: String,
    val appVersion: String
)