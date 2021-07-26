package com.example.trainingproject.models

data class Point(
    val result : List<PointResult>
)

data class PointResult(
    val totalPoint : Long,
    val currentPoint : Long,
    val levelUser : Int
)