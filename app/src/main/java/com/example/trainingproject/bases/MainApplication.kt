package com.example.trainingproject.bases

import android.app.Application
import android.content.Context

public class MainApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MainApplication? = null

        fun getApplicationContext() : Context {
            return instance!!.applicationContext
        }
    }
    override fun onCreate() {
        super.onCreate()
        val context: Context = MainApplication.getApplicationContext()
    }


}