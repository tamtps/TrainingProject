package com.example.trainingproject.screens
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.example.trainingproject.R
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseActivity
import com.example.trainingproject.bases.BaseDialog
import com.example.trainingproject.components.DrawerMenuAdapter
import com.example.trainingproject.components.SectionsPagerAdapter
import com.example.trainingproject.models.Menu
import com.example.trainingproject.models.Point
import com.example.trainingproject.models.PointResponse
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CardsActivity : BaseActivity() {
    lateinit var viewPager: ViewPager
    lateinit var tabs: TabLayout

    private lateinit var prefs : SharedPreferences
    var list :ArrayList<Menu> ?= null
    var listViewDrawer : ListView?= null
    private var itemAbout : TextView?= null
    private var itemLogOut : TextView?=null
    private var itemHowToVideo : TextView?=null
    var txtLevel : TextView? = null
    var txtDrawerPoint : TextView?= null
    private var txtName : TextView?= null
    private var imgAvatar  : ImageView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setDrawerView(R.layout.menu_drawer_mainscreen)
        leftIcon(R.drawable.btn_hamburger_white)
        centerImage(R.drawable.kanoo_white_icon)
        rightIcon(R.drawable.btn_home_white)

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
        listViewDrawer?.adapter = DrawerMenuAdapter(applicationContext, list!!)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)


        onLeftIcon()
        onAboutDrawer(version!!)

        onLogOut(prefs)

        onHowToVideo()
        getPointAPI(token!!, deviceId!!)
    }

    fun init(){
        viewPager = findViewById(R.id.view_pager)
        tabs  = findViewById(R.id.tabs)
        listViewDrawer = findViewById(R.id.list_view_drawer)
        itemAbout = findViewById(R.id.item_about)
        itemLogOut =findViewById(R.id.item_log_out)
        itemHowToVideo = findViewById(R.id.item_how_to_videos)
        txtLevel = findViewById(R.id.txt_level)
        txtDrawerPoint = findViewById(R.id.txt_drawer_point)
        txtName = findViewById(R.id.txt_name)
        imgAvatar = findViewById(R.id.img_avatar_menu)
    }

    fun onLeftIcon(){
        leftIcon.setOnClickListener(View.OnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        })
    }

    override fun getBodyLayout(): Int {
        return R.layout.activity_cards
    }

    override fun hasDrawer(): Boolean {
        return true
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
            var dialog = BaseDialog(CardsActivity@this)
            dialog.setContentView()
            dialog.title.text = getString(R.string.item_about)
            dialog.content.text = getString(R.string.beta_version)+ version + "\n" + getString(R.string.date) + date
            dialog.showCancelButton(false)
            dialog.onOKDismiss()
            dialog.show()
        })
    }
    public fun onLogOut(prefs : SharedPreferences){
        itemLogOut!!.setOnClickListener(View.OnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            var dialog = BaseDialog(CardsActivity@this)
            dialog.setContentView()
            dialog.title.text = getString(R.string.item_log_out)
            dialog.content.text  = getString(R.string.log_out_content)
            dialog.onCancelDismiss()
            dialog.show()
            dialog.buttonOK.setOnClickListener(View.OnClickListener {
                var intent = Intent(applicationContext, LogInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                prefs.edit().clear().commit()
                prefs.edit().putBoolean("firstStart", false).apply()
                finish()
            })
        })
    }
    public fun onHowToVideo() {
        itemHowToVideo!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, HowToVideoActivity::class.java))
        })
    }

    fun getPointAPI(token : String, deviceId:String){
        RetrofitClient().instance.getPoint(token!!, deviceId!!)
            .enqueue(object : retrofit2.Callback<PointResponse> {
                override fun onResponse(call: Call<PointResponse>, response: Response<PointResponse>) {
                    var point : String = NumberFormat.getNumberInstance(Locale.US).format(response.body()!!.result[0].currentPoint)
                    txtDrawerPoint!!.text = point + getString(R.string.points)
                    txtLevel!!.text = getString(R.string.level)+response.body()!!.result[0].levelUser.toString()+getString(R.string.verified)
                }

                override fun onFailure(call: Call<PointResponse>, t: Throwable) {
                    var dialog = BaseDialog(this@CardsActivity)
                    dialog.setContentView()
                    dialog.errorDialog(t.message)
                }

            })
    }

}