package com.example.trainingproject.components
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding
import com.example.trainingproject.R
import com.example.trainingproject.bases.BaseActivity
import com.example.trainingproject.bases.BaseDialog
import com.example.trainingproject.models.Menu
import com.example.trainingproject.screens.MainScreen
import java.util.*
import kotlin.collections.ArrayList

class DrawerMenuAdapter(var context: Context, var arrayList: ArrayList<Menu>) : BaseAdapter() {
    override fun getCount(): Int = arrayList.size

    override fun getItem(p0: Int): Any = arrayList.get(p0)

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view : View = View.inflate(context, R.layout.item_drawer_menu_main, null)
        var name : TextView = view.findViewById(R.id.txt_menu_name)
        var subList : ListView = view.findViewById(R.id.list_view_item_drawer)
        var menu : Menu = arrayList.get(p0)
        name.text = menu.name

        if ( menu.name.equals("")){
            name.visibility = View.GONE
        }
        subListView(menu, context, view)

        //set Color Clicked
        if (context.javaClass ==  menu.activity)
            name.setBackgroundColor(context.resources.getColor(R.color.item_choice))

        if (menu.list.size != 0) {
            name.updatePadding(bottom = 0)
        }
        return view
    }

    private fun subListView(menu: Menu, context:Context, view : View){
        var listItem: ArrayList<Menu> = ArrayList()
        var size : Int = menu.list.size
        var totalHeight:Int = 0
        for (index in 0 until size){
            listItem.add(menu.list[index])
        }
        var listView : ListView = view.findViewById(R.id.list_view_item_drawer)
        listView.adapter = DrawerSubMenuAdapter(context, listItem)
        listView.setOnItemClickListener{ parent, view, position, id ->
            onClickMenuDrawer(menu.list[position])
        }

        //set sub menu item height
        for (index in 0 until size){
            var list :View = listView.adapter.getView(index, null, listView)
            list.measure(0,0)
            totalHeight +=list.measuredHeight
        }
        var params : ViewGroup.LayoutParams = listView.layoutParams
        params.height = totalHeight +  (listView.dividerHeight * size-1)
        listView.layoutParams = params
    }

    fun onClickMenuDrawer(menu: Menu){
        when(menu.name){
            context.getString(R.string.item_log_out) ->  MainScreen().onLogOut(context)
            context.getString(R.string.item_about) -> MainScreen().onAbout(context)
        }
        if (menu.activity != null){
            var intent =Intent(context.applicationContext, menu.activity)
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)
        }
    }

}
