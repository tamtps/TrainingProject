package com.example.trainingproject.screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.trainingproject.R
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseActivity
import com.example.trainingproject.bases.BaseDialog
import com.example.trainingproject.components.DrawerMenuAdapter
import com.example.trainingproject.components.mainGridViewAdapter
import com.example.trainingproject.databinding.ActivityMainScreenBinding
import com.example.trainingproject.models.Menu
import com.example.trainingproject.models.Point
import com.example.trainingproject.models.Response
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import retrofit2.Call
import java.net.HttpURLConnection
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MainScreen() : BaseActivity<ActivityMainScreenBinding>() {
    var list: ArrayList<Menu>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        centerImage(R.drawable.kanoo_white_icon)
        rightIcon(R.drawable.icon_pay)

        //disable touch when loading data
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        setMenu()
        setDrawer()
        onMyWallet()
        getPointAPI(prefs.getString("uid", "0")!!.toLong())
    }


    override fun getViewBinding(): ActivityMainScreenBinding =  ActivityMainScreenBinding.bind(binding.root)
    override fun getBodyLayout(): Int = R.layout.activity_main_screen
    override fun hasDrawer(): Boolean = true

    private fun setMenu() {
        list = ArrayList()
        list = menuItem()
        bindingBody.gridviewMain?.adapter = mainGridViewAdapter(applicationContext, list!!)
    }

    private fun setDrawer() {
        bindingDrawer.txtName.text =
            prefs.getString("fname", "") + " " + prefs.getString("lname", "")
        Picasso.get().load(prefs.getString("avatar", "")).into(bindingDrawer.imgAvatarMenu)
    }

    private fun menuItem(): ArrayList<Menu> {
        var list: ArrayList<Menu> = ArrayList()
        list.add(Menu(getString(R.string.menu_market), R.drawable.icon_market,2))
        list.add(Menu(getString(R.string.menu_top_up), R.drawable.icon_topup))
        list.add(Menu(getString(R.string.menu_connections), R.drawable.icon_connect))
        list.add(Menu(getString(R.string.menu_cart), R.drawable.ic_my_cart, 4, listOf()))
        list.add(Menu(getString(R.string.menu_public_services), R.drawable.ic_public_services, 1))
        list.add(Menu(getString(R.string.menu_pay_bills), R.drawable.icon_bills))
        return list
    }

    private fun onMyWallet() {
        bindingBody.imgWallet!!.setOnClickListener(View.OnClickListener {
            var intent = Intent(applicationContext, CardsActivity::class.java)
            startActivity(intent)
        })
    }

    fun getPointAPI(uid: Long) {
        RetrofitClient().instance.getPoint(uid!!)
            .enqueue(object : retrofit2.Callback<Response<ArrayList<Point>>> {
                override fun onResponse(
                    call: Call<Response<ArrayList<Point>>>,
                    response: retrofit2.Response<Response<ArrayList<Point>>>
                ) {
                    if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
                        prefs.edit().clear()
                        prefs.edit().putBoolean("firstStart", false)
                        prefs.edit().apply()

                        //enable touch when load data success
                        bindingBody.progressCircular.visibility = View.INVISIBLE
                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                        var dialog = BaseDialog(this@MainScreen)
                        dialog.setContentView()
                        dialog.errorDialog(getString(R.string.login_again))
                        dialog.setCanceledOnTouchOutside(false)
                        dialog.binding.btnYes.setOnClickListener(View.OnClickListener {
                            var intent = Intent(applicationContext, LogInActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                            finish()
                        })
                    } else if (response.isSuccessful) {
                        //enable touch when load data success
                        bindingBody.progressCircular.visibility = View.INVISIBLE
                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                        var point: String = NumberFormat.getNumberInstance(Locale.US)
                            .format(response.body()!!.result[0].currentPoint)
                        bindingBody.txtPoint?.text = point
                        bindingDrawer.txtDrawerPoint!!.text =
                            point + " " + getString(R.string.points)
                        bindingDrawer.txtLevel!!.text =
                            getString(R.string.level) + " " + response.body()!!.result[0].levelUser.toString() + " " + getString(
                                R.string.verified
                            )
                    }
                }

                override fun onFailure(call: Call<Response<ArrayList<Point>>>, t: Throwable) {
                    var dialog = BaseDialog(this@MainScreen)
                    dialog.setContentView()
                    dialog.errorDialog(t.message)
                    dialog.dismiss()
                }

            })
    }
}
