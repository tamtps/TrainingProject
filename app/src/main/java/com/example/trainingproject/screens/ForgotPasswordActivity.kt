package com.example.trainingproject.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.trainingproject.R
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.databinding.ActivityForgotBinding
import com.example.trainingproject.models.CountryResponse
import com.example.trainingproject.models.CountryResult
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityForgotBinding
    private lateinit var countries : ArrayList<CountryResult>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getCountries()
        //TODO: Set methods dropdown
        val methods = resources.getStringArray(R.array.method)
        val arrayAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_item, methods)
        binding.txtMethodForgot.setAdapter(arrayAdapter)

        //TODO: Set countries dropdown
        //DO SOMETHING HERE


        binding.btnContinue.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, LogInActivity::class.java)
            startActivity(intent)
        })

        binding.txtMethodForgot.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0 -> {
                        binding.countryPicker.isVisible=true
                        binding.inputTxtForgot.setPadding(350,0,0,5)
                        binding.inputTxtForgot.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
                        binding.txtMethodInput.setText("Phone number")
                    }
                    1 -> {
                        binding.countryPicker.isVisible=false
                        binding.inputTxtForgot.setPadding(100,0,0,5)
                        binding.inputTxtForgot.inputType = InputType.TYPE_CLASS_TEXT
                        binding.txtMethodInput.setText("Email")
                    }

                }
            }
        })
    }

    private fun getCountries(){
        GlobalScope.launch(Dispatchers.IO){
            try{
                RetrofitClient().instance.getCountries()
                    .enqueue(object : Callback<CountryResponse>{
                        override fun onResponse(
                            call: Call<CountryResponse>,
                            response: Response<CountryResponse>
                        ) {
                            if(!response.body()?.result.isNullOrEmpty()){
                                countries = response.body()?.result!!
                            }

                            Log.d("Countries",countries.toString())

                        }

                        override fun onFailure(call: Call<CountryResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }

                    })
            }
            catch (e: Exception){
                Log.e("MAIN", "ERROR: ${e.message}")
            }

        }

    }
}