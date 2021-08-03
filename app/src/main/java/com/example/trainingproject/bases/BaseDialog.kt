package com.example.trainingproject.bases

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.trainingproject.R
import com.example.trainingproject.databinding.DialogSimpleBinding

open class BaseDialog(context: Context) : Dialog(context) {
    lateinit var binding: DialogSimpleBinding

    fun setContentView() {
        binding = DialogSimpleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun errorDialog(string : String?){
        binding.dialogTitle.text = context.getString(R.string.error)
        binding.dialogContent.text = string
        showCancelButton(false)
        onOKDismiss()
        show()
    }

    open fun showCancelButton(boolean: Boolean){
        if (boolean)   binding.btnNo.visibility = View.VISIBLE
        else binding.btnNo.visibility = View.INVISIBLE
    }

    fun onCancelDismiss(){
        binding.btnNo.setOnClickListener(View.OnClickListener { dismiss() })
    }
    fun onOKDismiss(){
        binding.btnYes.setOnClickListener(View.OnClickListener { dismiss() })
    }

}
