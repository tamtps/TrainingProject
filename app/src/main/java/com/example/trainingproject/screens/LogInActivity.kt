package com.example.trainingproject.screens

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.databinding.ActivityLoginBinding
import com.example.trainingproject.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection


class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.txtForgot.setOnClickListener({
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        })

        binding.btnLogIn.setOnClickListener(View.OnClickListener {
            val email = "filestringhoang01@gmail.com"
            val password = "qwerty1"
            if(email.isEmpty()){
                binding.txtEmailLogin.setError("Email required!")
                binding.txtEmailLogin.requestFocus()
            }
            else
            if(password.isEmpty()){
                binding.txtPwdLogin.setError("Password required!")
                binding.txtPwdLogin.requestFocus()
            }
            else
            LogIn(email, password)
        })
    }

    private fun LogIn(email: String, password: String) {
        //TODO: AUTHORIZATION HERE
        RetrofitClient().instance.userLogin(
            email,
            password,
            "ffffffff-bf45-43ae-ffff-ffffef05ac4a",
            "fELCd90Ftes%3AAPA91bHGdLvaGwrVds4gDsxdG7Fd6y2YMMGgX_q_yPnioSv1aygdvYgNdK5vViA0UtYvNY5ePox3PpVnnRa56PgDvqFrwjrnwU0AABodPkuMQWmAQmDgaOpTWLeCZaH7xG4TtwgpFNU9",
            "2",
            "androidvk",
        ).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                //TODO: IF STATUS OK SAVE TOKEN
                if(response.body()?.status == HttpURLConnection.HTTP_OK){
                    val prefs : SharedPreferences = applicationContext.getSharedPreferences("prefs", MODE_PRIVATE)
                    val editPref : SharedPreferences.Editor = prefs.edit()
                    editPref.putString("token", response.body()!!.result.loginInformation.token)
                    editPref.putString("fname", response.body()?.result?.accountInfo?.fname)
                    editPref.putString("lname", response.body()?.result?.accountInfo?.lname)
                    editPref.putString("avatar", response.body()?.result?.accountInfo?.flag)
                    editPref.putString("version", response.body()?.trace?.appVersion)

                    editPref.putBoolean("logged", true)
                    editPref.apply()
                    Toast.makeText(applicationContext, "Đăng nhập thành công", Toast.LENGTH_LONG,).show()
                    val intent = Intent(applicationContext, MainScreen::class.java)
                    startActivity(intent)
                    finish()
                }
                else Toast.makeText(applicationContext, response.body()?.statusCode, Toast.LENGTH_LONG,).show()

                Log.e("loginResponse", response.body().toString())
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })


    }
}