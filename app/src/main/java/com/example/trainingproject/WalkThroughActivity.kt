package com.example.trainingproject
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.trainingproject.walk_through.WalkThroughAdapter
class WalkThroughActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walk_through)
        var viewPaper : ViewPager = findViewById(R.id.WalkThroughViewPaper)
        viewPaper.adapter = WalkThroughAdapter(supportFragmentManager)
    }
    fun onGetStart(){
        startActivity(Intent(this, MainActivity::class.java))
    }
}