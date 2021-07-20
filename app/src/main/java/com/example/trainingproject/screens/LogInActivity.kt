package com.example.trainingproject.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.trainingproject.MainActivity
import com.example.trainingproject.databinding.ActivityLoginBinding


class LogInActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.txtForgot.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        })

        binding.btnLogIn.setOnClickListener(View.OnClickListener {
            LogIn()
        })
    }

    private fun LogIn(){
        //DO SOMETHING
        val intent = Intent(this, MainActivity::class.java)
    }
}