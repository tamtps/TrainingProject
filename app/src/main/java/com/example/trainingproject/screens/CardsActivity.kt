package com.example.trainingproject.screens

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import com.example.trainingproject.R
import com.example.trainingproject.components.PageViewModel
import com.example.trainingproject.components.PlaceholderFragment
import com.example.trainingproject.components.SectionsPagerAdapter


class CardsActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager
    lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)
        init()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

    fun init(){
        viewPager = findViewById(R.id.view_pager)
        tabs  = findViewById(R.id.tabs)
    }

}