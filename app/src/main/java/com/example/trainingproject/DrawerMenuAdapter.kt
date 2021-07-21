package com.example.trainingproject

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.trainingproject.models.Menu

class DrawerMenuAdapter(var context: Context, var arrayList: ArrayList<Menu>) : BaseAdapter() {
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
        var view : View = View.inflate(context, R.layout.item_drawer_menu_main, null)
        var name : TextView = view.findViewById(R.id.txt_menu_name)
        var menu : Menu = arrayList.get(p0)

        name.text = menu.name
        subListView(menu, context, view)

        return view
    }
}

fun subListView(menu: Menu, context:Context, view : View){
    var listItem: ArrayList<String> = ArrayList()
    var size : Int = menu.list.size
    var totalHeight:Int = 0
    for (index in 0 until size){
        listItem.add(menu.list[index])
    }

    var listView : ListView = view.findViewById(R.id.list_view_item_drawer)
    listView.adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listItem)

    for (index in 0 until size){
        var list :View = listView.adapter.getView(index, null, listView)
        list.measure(0,0)
        totalHeight +=list.measuredHeight
    }
    var params : ViewGroup.LayoutParams = listView.layoutParams
    params.height = totalHeight +  (listView.dividerHeight * size-1)
    params.width=400
    listView.layoutParams = params
}