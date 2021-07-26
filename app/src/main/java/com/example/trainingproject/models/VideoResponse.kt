package com.example.trainingproject.models

data class VideoResponse(
    val statusCode : String,
    val result: VideoResult
)

data class VideoResult(
    val content : ArrayList<Videos>
    )

data class Videos(
    val key : String,
    val value : String
)