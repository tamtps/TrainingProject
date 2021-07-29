package com.example.trainingproject.models

data class Point(
    val clientMessage: String,
    val countRecord: String,
    val statusCode: String,
    val status: Int,
    val result : List<PointResult>
)

data class PointResult(
    val totalPoint : Long,
    val currentPoint : Long,
    val levelUser : Int
)
