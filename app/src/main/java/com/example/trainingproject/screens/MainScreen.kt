package com.example.trainingproject.screens

import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.GridView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.example.trainingproject.R
import com.example.trainingproject.mainGridViewAdapter
import com.example.trainingproject.models.Menu
import com.google.android.material.navigation.NavigationView

class MainScreen : AppCompatActivity() {
    private lateinit var mainToolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mainDrawerLayout : DrawerLayout
    private lateinit var mainNavigationView : NavigationView
    private var mainGridView : GridView ?= null
    private var list :ArrayList<Menu> ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        init()
        actionToolbar()
        list = ArrayList()
        list = menuItem()
        mainGridView?.adapter = mainGridViewAdapter(applicationContext, list!!)
    }


    private fun init(){
        mainToolbar = findViewById(R.id.mainToolbar)
        mainDrawerLayout  = findViewById(R.id.mainDrawerLayout)
        mainNavigationView  = findViewById(R.id.mainNavitionView)
        mainGridView = findViewById(R.id.mainGridView)
    }

    private fun actionToolbar(){
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainToolbar.setNavigationIcon(R.drawable.btn_hamburger_white)
        mainToolbar.setNavigationOnClickListener(View.OnClickListener {
            mainDrawerLayout.openDrawer(GravityCompat.START)
        })
    }

    private fun menuItem() : ArrayList<Menu>{
        val list : ArrayList <Menu> = ArrayList()
        list.add(Menu("Market", R.drawable.icon_market))
        list.add(Menu("Top Up", R.drawable.icon_topup))
        list.add(Menu("Connections", R.drawable.icon_connect))
        list.add(Menu("Cart", R.drawable.ic_my_cart))
        list.add(Menu("Public services", R.drawable.ic_public_services))
        list.add(Menu("Pay bills", R.drawable.icon_bills))
        return list
    }
}