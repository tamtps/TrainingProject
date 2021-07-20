package com.example.trainingproject.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.example.trainingproject.R
import com.example.trainingproject.databinding.ActivityForgotBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityForgotBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val methods = resources.getStringArray(R.array.method)
        val arrayAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_item, methods)
        binding.txtMethodForgot.setAdapter(arrayAdapter)

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
}