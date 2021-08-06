package com.example.trainingproject.screens

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.trainingproject.BuildConfig
import com.example.trainingproject.R
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseDialog
import com.example.trainingproject.databinding.ActivityLoginBinding
import com.example.trainingproject.models.LoginResult
import com.example.trainingproject.models.Response
import retrofit2.Call
import retrofit2.Callback
import java.net.HttpURLConnection


class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var showPassword : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.txtPwdLogin.setOnFocusChangeListener { v, hasFocus ->
            run {
                if (hasFocus)
                    binding.txtPwdLogin.setHint("")
                else binding.txtPwdLogin.setHint("Password")
            }
        }

        binding.txtEmailLogin.setOnFocusChangeListener { v, hasFocus ->
            run {
                if (hasFocus)
                    binding.txtEmailLogin.setHint("")
                else binding.txtEmailLogin.setHint("Email")
            }
        }
        binding.txtBeta.text = "Beta version: ${BuildConfig.VERSION_NAME}"
        binding.imgShowPwd.setOnClickListener {
            showPassword = !showPassword
            when (showPassword) {
                false -> {
                    binding.imgShowPwd.setImageResource(R.drawable.eye_show)
                    binding.txtPwdLogin.transformationMethod = PasswordTransformationMethod.getInstance()

                }
                true -> {
                    binding.imgShowPwd.setImageResource(R.drawable.eye_hide)
                    binding.txtPwdLogin.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
            }
        }
        binding.txtForgot.setOnClickListener({
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        })

        var email = ""
        var password = ""

        binding.btnLogIn.setOnClickListener(View.OnClickListener {
//            "filestringhoang01@gmail.com"
            email = binding.txtEmailLogin.text.toString()
//            "qwerty1"
            password= binding.txtPwdLogin.text.toString()
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
        RetrofitClient().instance.userLogin(
            email,
            password,
            Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID),
            "fELCd90Ftes%3AAPA91bHGdLvaGwrVds4gDsxdG7Fd6y2YMMGgX_q_yPnioSv1aygdvYgNdK5vViA0UtYvNY5ePox3PpVnnRa56PgDvqFrwjrnwU0AABodPkuMQWmAQmDgaOpTWLeCZaH7xG4TtwgpFNU9",
            "2",
            "androidvk",
            android.os.Build.VERSION.SDK_INT.toString(),
            BuildConfig.VERSION_NAME,
        ).enqueue(object : Callback<Response<LoginResult>> {
            val prefs : SharedPreferences = applicationContext.getSharedPreferences("prefs", MODE_PRIVATE)
            val editPref : SharedPreferences.Editor = prefs.edit()
            override fun onResponse(call: Call<Response<LoginResult>>, response: retrofit2.Response<Response<LoginResult>>) {
                if(response.body()?.status == HttpURLConnection.HTTP_OK){
                    editPref.putString("token", response.body()!!.result.loginInformation.token)
                    editPref.putString("fname", response.body()?.result?.accountInfo?.fname)
                    editPref.putString("lname", response.body()?.result?.accountInfo?.lname)
                    editPref.putString("avatar", response.body()?.result?.accountInfo?.flag)
                    editPref.putString("version", response.body()?.trace?.appVersion)
                    editPref.putString("uid", response.body()!!.result.accountInfo.idUsers)
                    editPref.putBoolean("logged", true)
                    editPref.apply()
                    val intent = Intent(applicationContext, MainScreen::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    var dialog = BaseDialog(this@LogInActivity)
                    editPref.clear().apply()
                    dialog.setContentView()
                    dialog.errorDialog(getString(R.string.error_email_pass))
                }
            }

            override fun onFailure(call: Call<Response<LoginResult>>, t: Throwable) {
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