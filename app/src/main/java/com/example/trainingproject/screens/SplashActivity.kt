package com.example.trainingproject.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import com.example.trainingproject.MainActivity
import com.example.trainingproject.R

class SplashScreen : AppCompatActivity() {
    lateinit var handler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (firstStart) {
                var intent = Intent(this, WalkThroughActivity::class.java)
                startActivity(intent)
            }
            else {
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            finish()
        },3000)
    }
}