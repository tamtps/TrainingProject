package com.example.trainingproject.screens
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.trainingproject.MainActivity
import com.example.trainingproject.R
import com.example.trainingproject.walk_through.WalkThroughAdapter
class WalkThroughActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walk_through)
        var viewPaper : ViewPager = findViewById(R.id.WalkThroughViewPaper)
        viewPaper.adapter = WalkThroughAdapter(supportFragmentManager)

        var btnGetStart : Button = findViewById(R.id.btnGetStart)
        btnGetStart.setOnClickListener(View.OnClickListener {
            onGetStart()
            finish()
        })

        var btnSignIn : TextView = findViewById(R.id.btnSignIn)
        btnSignIn.setOnClickListener(View.OnClickListener {
            onSignIn()
            finish()
        })
    }
    fun onGetStart(){
        startActivity(Intent(this, MainActivity::class.java))
        var prefs : SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        var editPrefs : SharedPreferences.Editor = prefs.edit();
        editPrefs.putBoolean("firstStart", false)
        editPrefs.apply()
    }

    fun onSignIn(){
        startActivity(Intent(this, MainActivity::class.java))
    }
}