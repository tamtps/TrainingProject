package com.example.trainingproject.screens

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.trainingproject.BuildConfig
import com.example.trainingproject.R
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseDialog
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
                binding.txtEmailLogin.setError(getString(R.string.email_required))
                binding.txtEmailLogin.requestFocus()
            }
            else
                if(password.isEmpty()){
                    binding.txtPwdLogin.setError(getString(R.string.password_required))
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
            Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID),
            "fELCd90Ftes%3AAPA91bHGdLvaGwrVds4gDsxdG7Fd6y2YMMGgX_q_yPnioSv1aygdvYgNdK5vViA0UtYvNY5ePox3PpVnnRa56PgDvqFrwjrnwU0AABodPkuMQWmAQmDgaOpTWLeCZaH7xG4TtwgpFNU9",
            "2",
            "androidvk",
            android.os.Build.VERSION.SDK_INT.toString(),
            BuildConfig.VERSION_NAME,
        ).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
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
                    val intent = Intent(applicationContext, MainScreen::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    var dialog = BaseDialog(this@LogInActivity)
                    dialog.setContentView()
                    dialog.errorDialog(response.body()?.statusCode)
                }

                Log.e("loginResponse", response.body().toString())
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                var dialog = BaseDialog(this@LogInActivity)
                val prefs : SharedPreferences = applicationContext.getSharedPreferences("prefs", MODE_PRIVATE)
                val editPref : SharedPreferences.Editor = prefs.edit()
                editPref.clear().apply()
                dialog.setContentView()
                dialog.errorDialog(t.message)
            }

        })


    }
}