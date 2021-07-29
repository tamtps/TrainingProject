package com.example.trainingproject.bases

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.trainingproject.R

class BaseDialog(context: Context) : Dialog(context) {
    lateinit var title : TextView
    lateinit var content : TextView
    lateinit var buttonOK : Button
    lateinit var buttonCancel : Button

    fun setContentView() {
        super.setContentView(R.layout.dialog_simple)
        title = findViewById(R.id.dialog_title)
        content = findViewById(R.id.dialog_content)
        buttonOK = findViewById(R.id.btn_yes)
        buttonCancel = findViewById(R.id.btn_no)
    }

    fun showCancelButton(boolean: Boolean){
        if (boolean)   buttonCancel.visibility = View.VISIBLE
        else buttonCancel.visibility = View.INVISIBLE
    }

    fun onCancelDismiss(){
        buttonCancel.setOnClickListener(View.OnClickListener { dismiss() })
    }
    fun onOKDismiss(){
        buttonOK.setOnClickListener(View.OnClickListener { dismiss() })
    }

}