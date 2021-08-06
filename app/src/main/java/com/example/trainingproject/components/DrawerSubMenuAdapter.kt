package com.example.trainingproject.components

import android.content.Context
import android.graphics.Color
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.trainingproject.R

class DrawerSubMenuAdapter(var context: Context, var arrayList: ArrayList<com.example.trainingproject.models.Menu>) : BaseAdapter() {
    override fun getCount(): Int = arrayList.size

    override fun getItem(p0: Int): Any = arrayList.get(p0)

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view : View = View.inflate(context, R.layout.item_drawer_submenu, null)
        var name : TextView = view.findViewById(R.id.text_submenu_item)
        var menu : com.example.trainingproject.models.Menu = arrayList.get(p0)
        name.text = menu.name

        //set Color Clicked
        if (context.javaClass ==  menu.activity)
            name.setBackgroundColor(context.resources.getColor(R.color.item_choice))

        if (menu.name.equals(context.getString(R.string.terms_condition)) ||
            menu.name.equals(context.getString(R.string.item_about)) ||
            menu.name.equals(context.getString(R.string.item_log_out))) {}
        else {
            name.setPadding(
                context.resources.getDimension(R.dimen.padding_hor_submenu).toInt(),
                context.resources.getDimension(R.dimen.padding_ver_submenu).toInt(),
                0,
                context.resources.getDimension(R.dimen.padding_ver_submenu).toInt(),
            )
        }

        return view
    }
}