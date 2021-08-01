package com.example.trainingproject.screens

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.ContentInfoCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.trainingproject.R
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseActivity
import com.example.trainingproject.bases.BaseDialog
import com.example.trainingproject.components.DrawerMenuAdapter
import com.example.trainingproject.components.mainGridViewAdapter
import com.example.trainingproject.models.Menu
import com.example.trainingproject.models.Point
import com.example.trainingproject.models.Response
import com.example.trainingproject.models.Videos
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import retrofit2.Call
import java.net.HttpURLConnection
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MainScreen() : BaseActivity() {
    private lateinit var prefs : SharedPreferences
    private var mainGridView : GridView?= null
    var list :ArrayList<Menu> ?= null
    var listViewDrawer : ListView?= null
    private var itemAbout : TextView?= null
    private var itemLogOut : TextView?=null
    private var itemHowToVideo : TextView?=null
    var txtLevel : TextView? = null
    var txtPoint : TextView?= null
    var txtDrawerPoint : TextView?= null
    private var txtName : TextView?= null
    private var imgWallet : ImageView?= null
    private var imgAvatar  : ImageView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leftIcon(R.drawable.btn_hamburger_white)
        centerImage(R.drawable.kanoo_white_icon)
        setDrawerView(R.layout.menu_drawer_mainscreen)

        prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val token = prefs.getString("token","")
        val version = prefs.getString("version", "")
        val name = prefs.getString("fname","") + " " + prefs.getString("lname","")
        val avatar = prefs.getString("avatar","")
        val deviceId = prefs.getString("deviceId","")

        init()
        txtName!!.text = name
        Picasso.get().load(avatar).into(imgAvatar)
        list = ArrayList()
        list = menuItem()
        mainGridView?.adapter = mainGridViewAdapter(applicationContext, list!!)
        listViewDrawer?.adapter = DrawerMenuAdapter(applicationContext, list!!)

        onLeftIcon()
        onMyWallet()
        onAboutDrawer(version!!)
        onLogOut(prefs)
        onHowToVideo()
        getPointAPI(token!!)
    }


    fun onLeftIcon(){
        leftIcon.setOnClickListener(View.OnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        })
    }

    override fun getBodyLayout(): Int {
        return R.layout.activity_main_screen
    }

    override fun hasDrawer(): Boolean {
        return true
    }

    fun init(){
        mainGridView = findViewById(R.id.mainGridView)
        listViewDrawer = findViewById(R.id.list_view_drawer)
        itemAbout = findViewById(R.id.item_about)
        itemLogOut =findViewById(R.id.item_log_out)
        itemHowToVideo = findViewById(R.id.item_how_to_videos)
        txtPoint = findViewById(R.id.txt_point)
        txtLevel = findViewById(R.id.txt_level)
        txtDrawerPoint = findViewById(R.id.txt_drawer_point)
        txtName = findViewById(R.id.txt_name)
        imgWallet = findViewById(R.id.img_wallet)
        imgAvatar = findViewById(R.id.img_avatar_menu)
    }

    public fun menuItem() : ArrayList<Menu>{
        var list : ArrayList <Menu> = ArrayList()
        list.add(Menu(
            getString(R.string.menu_market),
            R.drawable.icon_market,
            2,
            listOf("Your Connection", "Your Order")
        ))
        list.add(Menu(getString(R.string.menu_top_up), R.drawable.icon_topup))
        list.add(Menu(getString(R.string.menu_connections), R.drawable.icon_connect))
        list.add(Menu(getString(R.string.menu_cart), R.drawable.ic_my_cart, 4, listOf()))
        list.add(Menu(getString(R.string.menu_public_services), R.drawable.ic_public_services,1))
        list.add(Menu(getString(R.string.menu_pay_bills), R.drawable.icon_bills))
        return list
    }

    public fun onAboutDrawer(version : String){
        itemAbout!!.setOnClickListener(View.OnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            var date : Date = Calendar.getInstance().time
            var dialog = BaseDialog(MainScreen@this)
            dialog.setContentView()
            dialog.title.text = getString(R.string.item_about)
            dialog.content.text = "Beta Version: "+ version + "\nDate: "+ date
            dialog.showCancelButton(false)
            dialog.onOKDismiss()
            dialog.show()
        })
    }
    public fun onLogOut(prefs : SharedPreferences){
        itemLogOut!!.setOnClickListener(View.OnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            var dialog = BaseDialog(MainScreen@this)
            dialog.setContentView()
            dialog.title.text = getString(R.string.item_log_out)
            dialog.content.text  = getString(R.string.log_out_content)
            dialog.onCancelDismiss()
            dialog.buttonOK.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
                var intent = Intent(applicationContext, LogInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity (intent)
                prefs.edit().clear().commit()
                prefs.edit().putBoolean("firstStart", false).apply()
                finish()
            })
            dialog.show()
        })
    }
    public fun onHowToVideo() {
        itemHowToVideo!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, HowToVideoActivity::class.java))
        })
    }
    private fun onMyWallet() {
        imgWallet!!.setOnClickListener(View.OnClickListener {
            var intent = Intent(applicationContext, CardsActivity::class.java)
            startActivity(intent)
        })
    }

    fun getPointAPI(token : String){
        RetrofitClient().instance.getPoint(token!!)
            .enqueue(object : retrofit2.Callback<Response<Point>> {
                override fun onResponse(call: Call<Response<Point>>, response: retrofit2.Response<Response<Point>>) {
                    if(response.code() == HttpURLConnection.HTTP_FORBIDDEN){
                        prefs.edit().clear()
                        prefs.edit().putBoolean("firstStart", false)
                        prefs.edit().apply()

                        var dialog = BaseDialog(this@MainScreen)
                        dialog.setContentView()
                        dialog.errorDialog(getString(R.string.login_again))
                        dialog.buttonOK.setOnClickListener(View.OnClickListener {
                            var intent = Intent(applicationContext, LogInActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                            finish()
                        })
                    }
                    else if(response.isSuccessful){
                        Log.d("RESPONSE_POINT", response.toString())

                        var point: String = NumberFormat.getNumberInstance(Locale.US)
                            .format(response.body()!!.result[0].currentPoint)
                        txtPoint?.text = point
                        txtDrawerPoint!!.text = point + " Points"
                        txtLevel!!.text =
                            "Level " + response.body()!!.result[0].levelUser.toString() + " Verified"
                    }
                }

                override fun onFailure(call: Call<Response<Point>>, t: Throwable) {
                    var dialog = BaseDialog(this@MainScreen)
                    dialog.setContentView()
                    dialog.errorDialog(t.message)
                    dialog.dismiss()
                }

            })
    }


}