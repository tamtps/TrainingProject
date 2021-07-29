package com.example.trainingproject.screens
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.widget.addTextChangedListener
import com.example.trainingproject.R
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseActivity
import com.example.trainingproject.bases.BaseDialog
import com.example.trainingproject.components.DrawerMenuAdapter
import com.example.trainingproject.components.SectionsPagerAdapter
import com.example.trainingproject.components.mainGridViewAdapter
import com.example.trainingproject.models.Menu
import com.example.trainingproject.models.Point
import com.example.trainingproject.screens.cards.WalletCardFragment
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

        //TODO: Lỗi đăng xuất, dùng FLAG.
//        onLogOut(prefs)

        onHowToVideo()
        getPointAPI(token!!)
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
            "Market",
            R.drawable.icon_market,
            2,
            listOf("Browse", "Your Connection", "Your Order")
        ))
        list.add(Menu("Top Up", R.drawable.icon_topup))
        list.add(Menu("Connections", R.drawable.icon_connect))
        list.add(Menu("Cart", R.drawable.ic_my_cart, 4, listOf()))
        list.add(Menu("Public services", R.drawable.ic_public_services,1))
        list.add(Menu("Pay bills", R.drawable.icon_bills))
        return list
    }

    public fun onAboutDrawer(version : String){
        itemAbout!!.setOnClickListener(View.OnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            var date : Date = Calendar.getInstance().time
            var dialog = BaseDialog(CardsActivity@this)
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
            var dialog = BaseDialog(CardsActivity@this)
            dialog.setContentView()
            dialog.title.text = getString(R.string.item_log_out)
            dialog.content.text  = getString(R.string.log_out_content)
            dialog.onCancelDismiss()
            dialog.buttonOK.setOnClickListener(View.OnClickListener {
                startActivity ( Intent(applicationContext, LogInActivity::class.java))
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

    fun getPointAPI(token : String){
        RetrofitClient().instance.getPoint(token!!)
            .enqueue(object : retrofit2.Callback<Point> {
                override fun onResponse(call: Call<Point>, response: Response<Point>) {
                    var point : String = NumberFormat.getNumberInstance(Locale.US).format(response.body()!!.result[0].currentPoint)
                    txtDrawerPoint!!.text = point + " Points"
                    txtLevel!!.text = "Level "+response.body()!!.result[0].levelUser.toString()+" Verified"
                }

                override fun onFailure(call: Call<Point>, t: Throwable) {
//                    onLogOut(prefs)
//                    Toast.makeText(applicationContext, "Error" +t.message, Toast.LENGTH_LONG).show()

                    var dialog = BaseDialog(this@CardsActivity)
                    dialog.setContentView()
                    dialog.title.text = getString(R.string.error)
                    dialog.content.text = t.message
                    dialog.showCancelButton(false)
                    dialog.onOKDismiss()
                    dialog.show()
                }

            })
    }

}