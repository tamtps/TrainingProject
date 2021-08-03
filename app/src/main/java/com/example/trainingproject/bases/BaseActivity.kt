package com.example.trainingproject.bases

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewbinding.ViewBinding
import com.example.trainingproject.R
import com.example.trainingproject.databinding.ActivityBaseBinding
import com.example.trainingproject.databinding.MenuDrawerMainscreenBinding
import com.example.trainingproject.screens.MainScreen

abstract class BaseActivity<bodyBinding: ViewBinding> : AppCompatActivity() {
    lateinit var binding: ActivityBaseBinding
    lateinit var bindingBody : bodyBinding
    lateinit var bindingDrawer: MenuDrawerMainscreenBinding


    abstract fun hasDrawer() : Boolean
    abstract fun getBodyLayout() : Int
    abstract fun getViewBinding() : bodyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!hasDrawer()) binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        setBodyView()
    }

    private fun setBodyView(){
        var bodyLayout : ViewStub = findViewById(R.id.body_layout)
        bodyLayout.layoutResource = getBodyLayout()
        bodyLayout.inflate()
        bindingBody= getViewBinding()
    }

    fun setDrawerView(layout : Int){
        var drawerView : ViewStub = findViewById(R.id.drawer)
        drawerView.layoutResource = layout
        drawerView.inflate()
        bindingDrawer = MenuDrawerMainscreenBinding.bind(binding.root)
    }

    fun leftIcon(drawableIcon : Int){
        binding.imgLeft.visibility = View.VISIBLE
        binding.imgLeft.setImageResource(drawableIcon)
    }

    fun rightIcon(drawableIcon : Int){
        binding.imgRight.visibility = View.VISIBLE
        binding.imgRight.setImageResource(drawableIcon)
        binding.imgRight.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MainScreen::class.java ))
        })
    }

    fun centerImage(drawableIcon : Int){
        binding.imgCenter.visibility = View.VISIBLE
        binding.imgCenter.setImageResource(drawableIcon)
    }

    fun centerText(text : String){
        binding.txtCenter.visibility = View.VISIBLE
        binding.txtCenter.text = text
    }

}