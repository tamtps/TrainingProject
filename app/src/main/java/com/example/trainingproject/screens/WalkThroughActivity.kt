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
    private lateinit var prefs : SharedPreferences
    private lateinit var editPrefs : SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walk_through)
        val viewPaper : ViewPager = findViewById(R.id.WalkThroughViewPaper)
        viewPaper.adapter = WalkThroughAdapter(supportFragmentManager)
        prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val btnGetStart : Button = findViewById(R.id.btnGetStart)
        btnGetStart.setOnClickListener(View.OnClickListener {
            onGetStart()
            finish()
        })

        val btnSignIn : TextView = findViewById(R.id.btnSignIn)
        btnSignIn.setOnClickListener(View.OnClickListener {
            onSignIn()
            finish()
        })
    }
    fun onGetStart(){
        startActivity(Intent(this, MainActivity::class.java))

        editPrefs = prefs.edit();
        editPrefs.putBoolean("firstStart", false)
        editPrefs.apply()
    }

    private fun onSignIn(){
        editPrefs = prefs.edit()
        val token = prefs.getString("token", "")
        val logged = prefs.getBoolean("logged", false)
        editPrefs.apply()
        if(logged && !token.isNullOrEmpty()){
            startActivity(Intent(this, MainScreen::class.java))
        }
        else {
            startActivity(Intent(this, LogInActivity::class.java))
        }

    }
}