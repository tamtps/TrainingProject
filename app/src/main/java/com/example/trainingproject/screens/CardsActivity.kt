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
    var list: ArrayList<Menu>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        centerImage(R.drawable.kanoo_white_icon)
        rightIcon(R.drawable.btn_home_white)

        bindingDrawer.txtName!!.text = prefs.getString("fname", "") + " " + prefs.getString("lname", "")
        Picasso.get().load(prefs.getString("avatar", "")).into(bindingDrawer.imgAvatarMenu)

        setTabs()
        val uid = prefs.getString("uid", "")
        getPointAPI(uid!!.toLong())
        onRightIcon()
    }

    override fun getViewBinding() = ActivityCardsBinding.bind(binding.root)
    override fun getBodyLayout() = R.layout.activity_cards
    override fun hasDrawer() = true

    fun onRightIcon(){
        binding.imgRight.setOnClickListener(View.OnClickListener {
            var intent = Intent(applicationContext, MainScreen::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        })
    }

    private fun setTabs(){
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        bindingBody.viewPager.adapter = sectionsPagerAdapter
        bindingBody.tabs.setupWithViewPager(bindingBody.viewPager)
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