package com.example.trainingproject.screens

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
        binding.txtMethod.setAdapter(arrayAdapter)

        binding.txtMethod.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0 -> {
                        binding.ccp.isVisible=true
                        binding.inputTxt.setPadding(350,0,0,5)
                        binding.inputTxt.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
                        binding.txtMethodInput.setText("Phone number")
                    }
                    1 -> {
                        binding.ccp.isVisible=false
                        binding.inputTxt.setPadding(100,0,0,5)
                        binding.inputTxt.inputType = InputType.TYPE_CLASS_TEXT
                        binding.txtMethodInput.setText("Email")
                    }

                }
            }
        })
    }
}