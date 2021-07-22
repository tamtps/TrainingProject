package com.example.trainingproject

import android.app.ActionBar
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.trainingproject.models.Menu

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

        return view
    }

}