package com.example.trainingproject.components

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import com.example.trainingproject.R
import com.example.trainingproject.models.Menu
import com.example.trainingproject.screens.MainScreen

class mainGridViewAdapter (var context: Context, var arrayList: ArrayList<Menu>): BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        return arrayList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view:View = View.inflate(context, R.layout.item_gridview_menu_main, null)
        var icons : ImageView = view.findViewById(R.id.img_grid_icon)
        var names : TextView = view.findViewById(R.id.txt_grid_name)
        var notification : TextView = view.findViewById(R.id.txt_notification)

        var menu : Menu = arrayList.get(p0)
        icons.setImageResource(menu.icon!!)
        names.text = menu.name!!

        notification.text = menu.notification.toString()!!
        if(menu.notification == 0) {
            notification.isVisible = false
        }

        view.setOnClickListener(View.OnClickListener {
            if (menu.activity!=MainScreen::class.java){
                val intent = Intent(context, menu.activity)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(context, intent, null)
            }
        })

        return view
    }
}