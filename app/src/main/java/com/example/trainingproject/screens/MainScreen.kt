package com.example.trainingproject.screens
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.example.trainingproject.DrawerMenuAdapter
import com.example.trainingproject.R
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.mainGridViewAdapter
import com.example.trainingproject.models.Menu
import com.example.trainingproject.models.Point
import com.google.android.material.navigation.NavigationView
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.ArrayList

class MainScreen : AppCompatActivity() {
    lateinit var mainToolbar: androidx.appcompat.widget.Toolbar
    lateinit var mainDrawerLayout : DrawerLayout
    lateinit var mainNavigationView : NavigationView
    var mainGridView : GridView ?= null
    var list :ArrayList<Menu> ?= null
    var listViewDrawer : ListView?= null
    var itemAbout : TextView ?= null
    var itemLogOut : TextView ?=null
    var itemHowToVideo : TextView ?=null
    var txtLevel : TextView ? = null
    var imgWallet : ImageView ?= null
    var txtPoint : TextView ?= null
    var txtDrawerPoint : TextView ?= null


    val token = "1dc8e6b9-e21c-410a-becc-bbb923f4c22a"
//    TODO: Token shared Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        init()
        actionToolbar()
        list = ArrayList()
        list = menuItem()
        mainGridView?.adapter = mainGridViewAdapter(applicationContext, list!!)
        listViewDrawer?.adapter = DrawerMenuAdapter(applicationContext, list!!)
        onMyWallet()
        onAboutDrawer()
        onLogOut()
        onHowToVideo()
        getPointAPI()
    }

    fun init(){
        mainToolbar = findViewById(R.id.mainToolbar)
        mainDrawerLayout  = findViewById(R.id.mainDrawerLayout)
        mainNavigationView  = findViewById(R.id.mainNavitionView)
        mainGridView = findViewById(R.id.mainGridView)
        listViewDrawer = findViewById(R.id.list_view_drawer)
        itemAbout = findViewById(R.id.item_about)
        itemLogOut =findViewById(R.id.item_log_out)
        itemHowToVideo = findViewById(R.id.item_how_to_videos)
        imgWallet = findViewById(R.id.img_wallet)
        txtPoint = findViewById(R.id.txt_point)
        txtLevel = findViewById(R.id.txt_level)
        txtDrawerPoint = findViewById(R.id.txt_drawer_point)
    }

    fun actionToolbar(){
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainToolbar.setNavigationIcon(R.drawable.btn_hamburger_white)
        mainToolbar.setNavigationOnClickListener(View.OnClickListener {
            mainDrawerLayout.openDrawer(GravityCompat.START)
        })
    }

    private fun menuItem() : ArrayList<Menu>{
        var list : ArrayList <Menu> = ArrayList()
        list!!.add(Menu(
            "Market",
            R.drawable.icon_market,
            2,
            listOf("Browse", "Your Connection", "Your Order")
        ))
        list!!.add(Menu("Top Up", R.drawable.icon_topup))
        list!!.add(Menu("Connections", R.drawable.icon_connect))
        list!!.add(Menu("Cart", R.drawable.ic_my_cart, 4, listOf()))
        list!!.add(Menu("Public services", R.drawable.ic_public_services,1))
        list!!.add(Menu("Pay bills", R.drawable.icon_bills))
        return list
    }

    private fun onAboutDrawer(){
        itemAbout!!.setOnClickListener(View.OnClickListener {
            mainDrawerLayout.closeDrawer(GravityCompat.START)
            var dialog = Dialog(MainScreen@this)
            dialog.setContentView(R.layout.dialog_simple)

            var title: TextView = dialog.findViewById(R.id.dialog_title)
            var content : TextView = dialog.findViewById(R.id.dialog_content)
            var btn_yes  : Button = dialog.findViewById(R.id.btn_yes)
            var btn_no : Button = dialog.findViewById(R.id.btn_no)

            title.text = getString(R.string.item_about)
            btn_no.isVisible = false
            btn_yes.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
            })
            //            content.text =
            //                TODO: ABOUT BETA VERSION & DATE
            dialog.show()
        })
    }
    private fun onLogOut(){
        itemLogOut!!.setOnClickListener(View.OnClickListener {
            mainDrawerLayout.closeDrawer(GravityCompat.START)
            var dialog = Dialog(MainScreen@this)
            dialog.setContentView(R.layout.dialog_simple)
            var title: TextView = dialog.findViewById(R.id.dialog_title)
            var content : TextView = dialog.findViewById(R.id.dialog_content)
            var btn_yes  : Button = dialog.findViewById(R.id.btn_yes)
            var btn_no : Button = dialog.findViewById(R.id.btn_no)
            title.text = getString(R.string.item_log_out)
            content.text  = getString(R.string.log_out_content)
            btn_no.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
            })
            btn_yes.setOnClickListener(View.OnClickListener {
                //TODO: LOG OUT
            })
            dialog.show()
        })
    }
    private fun onHowToVideo() {
        itemHowToVideo!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, HowToVideoActivity::class.java))
        })
    }
    private fun onMyWallet() {
        imgWallet!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, CardsActivity::class.java))
        })
    }

    fun getPointAPI(){
        RetrofitClient().videoInstance.getPoint(token)
            .enqueue(object : retrofit2.Callback<Point> {
                override fun onResponse(call: Call<Point>, response: Response<Point>) {
                    var point : String = NumberFormat.getNumberInstance(Locale.US).format(response.body()!!.result[0].currentPoint)
                    txtPoint!!.text = point
                    txtDrawerPoint!!.text = point + " Points"
                    txtLevel!!.text = "Level "+response.body()!!.result[0].levelUser.toString()+" Verified"
                }

                override fun onFailure(call: Call<Point>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error" +t.message, Toast.LENGTH_LONG).show()
                }

            })
    }
}
