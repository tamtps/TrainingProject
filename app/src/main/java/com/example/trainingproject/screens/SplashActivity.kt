package com.example.trainingproject.screens

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import com.example.trainingproject.R

class SplashActivity : AppCompatActivity() {
    lateinit var handler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)

        val prefs : SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        val editPrefs = prefs.edit()
        val logged = prefs.getBoolean("logged", false)
        val token = prefs.getString("token", "")
        val firstStart = prefs.getBoolean("firstStart",true)

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if(firstStart){
                editPrefs.putBoolean("firstStart", false)
                editPrefs.apply()
                val intent = Intent(this, WalkThroughActivity::class.java)
                startActivity(intent)
            }
            else{
                if (logged && !token.isNullOrEmpty()) {
                    val intent = Intent(this, MainScreen::class.java)
                    startActivity(intent)
                }
                else {
                    val intent = Intent(this, LogInActivity::class.java)
                    startActivity(intent)
                }
            }

            finish()
        },2)
    }


}