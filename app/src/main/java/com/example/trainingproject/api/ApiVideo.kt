package com.example.trainingproject.api

import com.example.trainingproject.models.VideoResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET

interface ApiVideo {
    @GET("api/support?category=1")
    fun getVideo(): Call<VideoResponse>
}