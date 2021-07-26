package com.example.trainingproject.api

import com.example.trainingproject.models.VideoResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ApiVideo {
    @GET("api/support?category=1")
    fun getVideo(
        @Header("token") token: String
    ): Call<VideoResponse>
}