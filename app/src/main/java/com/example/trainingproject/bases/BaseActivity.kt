package com.example.trainingproject.bases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import com.example.trainingproject.R

abstract class BaseActivity : AppCompatActivity() {
    lateinit var leftIcon : ImageView
    lateinit var rightIcon : ImageView
    lateinit var centerImage : ImageView
    lateinit var centerText : TextView
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        leftIcon = findViewById(R.id.img_left)
        rightIcon = findViewById(R.id.img_right)
        centerImage = findViewById(R.id.img_center)
        centerText = findViewById(R.id.txt_center)
        drawerLayout = findViewById(R.id.drawer_layout)

        if (!hasDrawer()) drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        var bodyLayout : ViewStub = findViewById(R.id.body_layout)
        bodyLayout.layoutResource = getBodyLayout()
        bodyLayout.inflate()
    }

    abstract fun getBodyLayout() : Int
    abstract fun hasDrawer() : Boolean

    fun setDrawerView(layout : Int){
        var drawerView : ViewStub = findViewById(R.id.drawer)
        drawerView.layoutResource = layout
        drawerView.inflate()
    }

    fun leftIcon(drawableIcon : Int){
        leftIcon.visibility = View.VISIBLE
        leftIcon.setImageResource(drawableIcon)
    }

    fun rightIcon(drawableIcon : Int){
        rightIcon.visibility = View.VISIBLE
        rightIcon.setImageResource(drawableIcon)
    }

    fun centerImage(drawableIcon : Int){
        centerImage.visibility = View.VISIBLE
        centerImage.setImageResource(drawableIcon)
    }

    fun centerText(text : String){
        centerText.visibility = View.VISIBLE
        centerText.text = text
    }
}