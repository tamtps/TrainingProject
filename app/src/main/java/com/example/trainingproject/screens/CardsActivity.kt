package com.example.trainingproject.screens

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.example.trainingproject.R
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseActivity
import com.example.trainingproject.bases.BaseDialog
import com.example.trainingproject.components.DrawerMenuAdapter
import com.example.trainingproject.components.SectionsPagerAdapter
import com.example.trainingproject.databinding.ActivityCardsBinding
import com.example.trainingproject.models.Menu
import com.example.trainingproject.models.Point
import com.example.trainingproject.models.Response
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import retrofit2.Call
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CardsActivity : BaseActivity<ActivityCardsBinding>() {
    private lateinit var prefs: SharedPreferences
    var list: ArrayList<Menu>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leftIcon(R.drawable.btn_hamburger_white)
        centerImage(R.drawable.kanoo_white_icon)
        rightIcon(R.drawable.btn_home_white)
        setDrawerView(R.layout.menu_drawer_mainscreen)

        prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val version = prefs.getString("version", "")
        val name = prefs.getString("fname", "") + " " + prefs.getString("lname", "")
        val avatar = prefs.getString("avatar", "")
        val uid = prefs.getString("uid", "")

        bindingDrawer.txtName!!.text = name
        Picasso.get().load(avatar).into(bindingDrawer.imgAvatarMenu)
        list = ArrayList()
        list = menuItem()
        bindingDrawer.listViewDrawer?.adapter = DrawerMenuAdapter(applicationContext, list!!)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        bindingBody.viewPager.adapter = sectionsPagerAdapter
        bindingBody.tabs.setupWithViewPager(bindingBody.viewPager)

        onLeftIcon()
        onAboutDrawer(version!!)
        onLogOut(prefs)
        onHowToVideo()
        getPointAPI(uid!!.toLong())
    }

    override fun getViewBinding() = ActivityCardsBinding.inflate(layoutInflater)
    override fun getBodyLayout() = R.layout.activity_cards
    override fun hasDrawer() = true

    fun onLeftIcon() {
        binding.imgLeft.setOnClickListener(View.OnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        })
    }

    public fun menuItem(): ArrayList<Menu> {
        var list: ArrayList<Menu> = ArrayList()
        list.add(
            Menu(
                getString(R.string.menu_market),
                R.drawable.icon_market,
                2,
                listOf("Your Connection", "Your Order")
            )
        )
        list.add(Menu(getString(R.string.menu_top_up), R.drawable.icon_topup))
        list.add(Menu(getString(R.string.menu_connections), R.drawable.icon_connect))
        list.add(Menu(getString(R.string.menu_cart), R.drawable.ic_my_cart, 4, listOf()))
        list.add(Menu(getString(R.string.menu_public_services), R.drawable.ic_public_services, 1))
        list.add(Menu(getString(R.string.menu_pay_bills), R.drawable.icon_bills))
        return list
    }

    public fun onAboutDrawer(version: String) {
        bindingDrawer.itemAbout!!.setOnClickListener(View.OnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            var date: Date = Calendar.getInstance().time
            var dialog = BaseDialog(CardsActivity@ this)
            dialog.setContentView()
            dialog.binding.dialogTitle.text = getString(R.string.item_about)
            dialog.binding.dialogContent.text = getString(R.string.beta_version) + version + "\n" + getString(R.string.date) + date
            dialog.showCancelButton(false)
            dialog.onOKDismiss()
            dialog.show()
        })
    }

    public fun onLogOut(prefs: SharedPreferences) {
        bindingDrawer.itemLogOut!!.setOnClickListener(View.OnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            var dialog = BaseDialog(CardsActivity@ this)
            dialog.setContentView()
            dialog.binding.dialogTitle.text = getString(R.string.item_log_out)
            dialog.binding.dialogContent.text = getString(R.string.log_out_content)
            dialog.onCancelDismiss()
            dialog.binding.btnYes.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
                var intent = Intent(applicationContext, LogInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                prefs.edit().clear().commit()
                prefs.edit().putBoolean("firstStart", false).apply()
                finish()
            })
            dialog.show()
        })
    }

    public fun onHowToVideo() {
        bindingDrawer.itemHowToVideos!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, HowToVideoActivity::class.java))
        })
    }

    fun getPointAPI( uid: Long) {
        RetrofitClient().instance.getPoint(uid)
            .enqueue(object :
                retrofit2.Callback<Response<ArrayList<Point>>> {
                override fun onResponse(
                    call: Call<Response<ArrayList<Point>>>,
                    response: retrofit2.Response<Response<ArrayList<Point>>>
                ) {
                    val point: String = NumberFormat.getNumberInstance(Locale.US)
                        .format(response.body()!!.result[0].currentPoint)
                    bindingDrawer.txtDrawerPoint!!.text = point + getString(R.string.points)
                    bindingDrawer.txtLevel!!.text =
                        getString(R.string.level) + response.body()!!.result[0].levelUser.toString() + getString(
                            R.string.verified
                        )
                }

                override fun onFailure(call: Call<Response<ArrayList<Point>>>, t: Throwable) {
                    var dialog = BaseDialog(this@CardsActivity)
                    dialog.setContentView()
                    dialog.errorDialog(t.message)
                }

            })
    }
}