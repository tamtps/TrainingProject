package com.example.trainingproject.screens

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import com.example.trainingproject.MainActivity
import com.example.trainingproject.R

class SplashActivity : AppCompatActivity() {
    lateinit var handler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)

        val prefs : SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        val firstStart : Boolean =  prefs.getBoolean("firstStart", true)

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (firstStart) {
                val intent = Intent(this, WalkThroughActivity::class.java)
                startActivity(intent)
            }
            else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            finish()
        },3000)
    }
}