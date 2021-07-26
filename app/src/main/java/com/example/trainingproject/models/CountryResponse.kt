package com.example.trainingproject.models

data class CountryResponse(
    val countRecord: Int,
    val statusCode: String,
    val status: String,
    val result: ArrayList<CountryResult>
)

data class CountryResult(
    val name: String,
    val alpha2Code: String,
    val callingCodes: String,
    val flag: String,
    val regex: String,
    var favorite: Boolean,
)