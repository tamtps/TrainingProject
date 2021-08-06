package com.example.trainingproject.screens

import android.app.Dialog
import android.content.Intent
import android.graphics.Canvas
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingproject.R
import com.example.trainingproject.bases.BaseDialog
import com.example.trainingproject.components.CountryPickerAdapter
import com.example.trainingproject.databinding.ActivityForgotBinding
import com.example.trainingproject.viewmodels.CountryPickerViewModel
import com.squareup.picasso.Picasso

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotBinding
    private lateinit var dialog : Dialog
    private lateinit var countryPickerAdapter : CountryPickerAdapter
    private var methodType : Int = 0
    private lateinit var regex : String
    private lateinit var methods : Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViewModel()
        bindingComponent()

    }

    fun bindingComponent(){


        //TODO: Set methods dropdown
        methods = resources.getStringArray(R.array.method)
        val arrayAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_item, methods)
        binding.txtMethodForgot.setAdapter(arrayAdapter)

        //TODO: Set country picker dialog
        //DO SOMETHING HERE
        binding.selectCountry.setOnClickListener(View.OnClickListener {
            dialog.show()
        })

        binding.btnContinue.setOnClickListener(View.OnClickListener {
            var valid = true
            when(methodType){
                0 -> {
                    val phone = "${binding.txtCountryCodeSelect.text}${binding.inputTxtForgot.text}"
                    if(!isValidPhone(phone)){
                        var dialog = BaseDialog(this)
                        dialog.setContentView()
                        dialog.errorDialog(getString(R.string.invalid_phone))
                        valid = false
                    }
                    else {
                        valid = true
                        var dialog = BaseDialog(this)
                        dialog.setContentView()
                        dialog.errorDialog(getString(R.string.phone_not_registered))
                        valid = false
                    }
                }
                1 -> {
                    if(!isValidEmail(binding.inputTxtForgot.text.toString())){

                        var dialog = BaseDialog(this)
                        dialog.setContentView()
                        dialog.errorDialog(getString(R.string.wrong_email))
                        valid = false
                    }
                    else {
                        valid = true
                        var dialog = BaseDialog(this)
                        dialog.setContentView()
                        dialog.errorDialog(getString(R.string.email_not_registered))
                        valid = false
                    }
                }

            }
            if(valid)
                startActivity(Intent(applicationContext, LogInActivity::class.java))
            else
                binding.inputTxtForgot.requestFocus()
        })

        binding.txtMethodForgot.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        binding.inputTxtForgot.inputType = InputType.TYPE_CLASS_NUMBER
                        binding.txtMethodInput.setText("Phone number")
                        binding.inputTxtForgot.setText("")
                        binding.imgFlagSelect.visibility = View.VISIBLE
                        binding.imgArrowDown.visibility = View.VISIBLE
                        binding.txtCountryCodeSelect.visibility = View.VISIBLE
                        binding.txtInstruction.setText(R.string.phone_number_recovery_instruction)
                        methodType = 0
                    }
                    1 -> {
                        binding.inputTxtForgot.inputType = InputType.TYPE_CLASS_TEXT
                        binding.txtMethodInput.setText("Email")
                        binding.inputTxtForgot.setText("")
                        binding.imgFlagSelect.visibility = View.GONE
                        binding.imgArrowDown.visibility = View.GONE
                        binding.txtCountryCodeSelect.visibility = View.GONE
                        binding.txtInstruction.setText(R.string.email_recovery_instruction)
                        methodType = 1
                    }

                }
            }
        })
    }

    private fun initViewModel(dialog: Dialog){
        val recView = dialog.findViewById<RecyclerView>(R.id.recViewCountry)
            recView.layoutManager = LinearLayoutManager(applicationContext)
            recView.adapter = countryPickerAdapter
            recView.addItemDecoration(object : DividerItemDecoration(applicationContext, LinearLayoutManager.HORIZONTAL){
                override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

                }
            })

        dialog.findViewById<ImageButton>(R.id.btnCloseDialog)
            .setOnClickListener(View.OnClickListener {
                dialog.dismiss()
            })

        dialog.findViewById<EditText>(R.id.searchCountry).addTextChangedListener(object:
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                countryPickerAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {
                countryPickerAdapter.filter.filter(s)
            }

        })

    }

    private fun initDialog(){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.country_picker_dialog)
        dialog.setCancelable(false)
        val window: Window? = dialog.window

        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun initViewModel(){
        dialog = Dialog(this)
        initDialog()
        countryPickerAdapter = CountryPickerAdapter(binding ,dialog)

        val attributes: WindowManager.LayoutParams? = window?.attributes
        attributes?.gravity = Gravity.CENTER
        window?.attributes = attributes
        initViewModel(dialog)

        val viewModel = ViewModelProvider(this).get(CountryPickerViewModel::class.java)
        //Observer Live Data
        viewModel.getListObserver().observe(this, {
                if(it.result.isNotEmpty()){
                dialog.findViewById<ProgressBar>(R.id.progress_circular_country_picker).visibility = View.INVISIBLE
                countryPickerAdapter.setUpdatedData(it.result)
                it.result.forEach { country ->
                    if (country.favorite) {
                        Picasso.get().load(country.flag).into(binding.imgFlagSelect)
                        binding.txtCountryCodeSelect.text = country.callingCodes
                        regex = country.regex
                    }
                }
            }
            else {
                var dialog = BaseDialog(this)
                dialog.setContentView()
                dialog.errorDialog(getString(R.string.error_getting_data))
            }
        })

        viewModel.makeApiCall()
    }

    fun isValidEmail(email: String)  : Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPhone(phone: String) : Boolean {
        return !TextUtils.isEmpty(phone) && android.util.Patterns.PHONE.matcher(phone).matches()
    }
}