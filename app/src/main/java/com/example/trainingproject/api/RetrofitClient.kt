package com.example.trainingproject.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    val interceptor = HttpLoggingInterceptor()



    private val BASE_URL = "https://kanoo-gateway-staging.kardsys.com/visikard/"
    private val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .method(original.method, original.body)
            .addHeader("deviceId", "ffffffff-bf45-43ae-ffff-ffffef05ac4a")
            .addHeader("appVersion", "1.3.0.17")
            .addHeader("app-platform", "Kanoo-Android")
            .addHeader("X-TENANT", "kanoo")
            .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")

        val request = requestBuilder.build()
        chain.proceed(request)
    }
        .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))

    .build()

    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(Api::class.java)
    }

    val videoInstance : ApiVideo by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(ApiVideo::class.java)
    }
}