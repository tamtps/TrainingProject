package com.example.trainingproject.bases

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.view.ViewStub
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.load.engine.Resource
import com.example.trainingproject.R
import com.example.trainingproject.components.DrawerMenuAdapter
import com.example.trainingproject.databinding.ActivityBaseBinding
import com.example.trainingproject.databinding.MenuDrawerMainscreenBinding
import com.example.trainingproject.models.Menu
import com.example.trainingproject.screens.CardsActivity
import com.example.trainingproject.screens.HowToVideoActivity
import com.example.trainingproject.screens.LogInActivity
import com.example.trainingproject.screens.MainScreen
import java.util.*
import kotlin.collections.ArrayList

abstract class BaseActivity<bodyBinding: ViewBinding> : AppCompatActivity() {
    lateinit var binding: ActivityBaseBinding
    lateinit var bindingBody : bodyBinding
    lateinit var bindingDrawer: MenuDrawerMainscreenBinding
    lateinit var prefs: SharedPreferences


    abstract fun hasDrawer() : Boolean
    abstract fun getBodyLayout() : Int
    abstract fun getViewBinding() : bodyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs  = application.getSharedPreferences("prefs", MODE_PRIVATE)

        if (!hasDrawer()) binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        else {
            setDrawerView(R.layout.menu_drawer_mainscreen)
            leftIcon(R.drawable.btn_hamburger_white)
            binding.imgLeft.setOnClickListener(View.OnClickListener {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            })
        }
        setBodyView()
    }

    private fun setBodyView(){
        var bodyLayout : ViewStub = findViewById(R.id.body_layout)
        bodyLayout.layoutResource = getBodyLayout()
        bodyLayout.inflate()
        bindingBody= getViewBinding()
    }

    private fun setDrawerView(layout : Int){
        //set Drawer View layout
        var drawerView : ViewStub = findViewById(R.id.drawer)
        drawerView.layoutResource = layout
        drawerView.inflate()
        bindingDrawer = MenuDrawerMainscreenBinding.bind(binding.root)
        binding.drawerLayout.addDrawerListener(customDrawer())
        binding.drawerLayout.setScrimColor(Color.TRANSPARENT)
        //drawer width
        binding.navigationView.layoutParams.width = (resources.displayMetrics.widthPixels*0.85).toInt()

        //Set list item
        setDrawerMenuItem()
    }

    fun leftIcon(drawableIcon : Int){
        binding.imgLeft.visibility = View.VISIBLE
        binding.imgLeft.setImageResource(drawableIcon)
    }

    fun rightIcon(drawableIcon : Int){
        binding.imgRight.visibility = View.VISIBLE
        binding.imgRight.setImageResource(drawableIcon)
    }

    fun centerImage(drawableIcon : Int){
        binding.imgCenter.visibility = View.VISIBLE
        binding.imgCenter.setImageResource(drawableIcon)
    }

    fun centerText(text : String){
        binding.txtCenter.visibility = View.VISIBLE
        binding.txtCenter.text = text
    }

    override fun onBackPressed() {
        if(hasDrawer() && binding.drawerLayout?.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else super.onBackPressed()
    }

    fun onAbout(context: Context){
        prefs = context.getSharedPreferences("version", MODE_PRIVATE)
        var date: Date = Calendar.getInstance().time
        var dialog = BaseDialog(context)
        dialog.setContentView()
        dialog.binding.dialogTitle.text = context.getString(R.string.item_about)
        dialog.binding.dialogContent.text =
            context.getString(R.string.beta_version) + prefs.getString("version","") + "\n" + context.getString(R.string.date) + date
        dialog.showCancelButton(false)
        dialog.onOKDismiss()
        dialog.show()
    }

    fun onLogOut(context: Context) {
        prefs = context.getSharedPreferences("version", MODE_PRIVATE)
        var dialog = BaseDialog(context)
        dialog.setContentView()
        dialog.binding.dialogTitle.text = context.getString(R.string.item_log_out)
        dialog.binding.dialogContent.text = context.getString(R.string.log_out_content)
        dialog.onCancelDismiss()
        dialog.binding.btnYes.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            var intent = Intent(context.applicationContext, LogInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
            prefs.edit().clear().commit()
            prefs.edit().putBoolean("firstStart", false).apply()
            finish()
        })
        dialog.show()
    }

    inner class customDrawer : DrawerLayout.DrawerListener{
        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            val slideX = drawerView.width * slideOffset
            binding.layoutMain.setTranslationX(slideX)
        }

        override fun onDrawerOpened(drawerView: View) {
        }

        override fun onDrawerClosed(drawerView: View) {
        }

        override fun onDrawerStateChanged(newState: Int) {
        }
    }

    private fun setDrawerMenuItem(){
        var listDrawer : ArrayList<Menu> = ArrayList()
        listDrawer.add(Menu("Home", MainScreen::class.java))
        listDrawer.add(
            Menu("Your Wallet", listOf(
                Menu("Wallet", CardsActivity::class.java),
                Menu("Update Wallet Pin"), Menu("Transaction History")
            ))
        )
        listDrawer.add(Menu("Find Businesses & People", listOf()))
        listDrawer.add(Menu("Messages", listOf()))
        listDrawer.add(
            Menu("Market", listOf(
                Menu("Browse"),
                Menu( "Your Connections"),
                Menu( "Your Orders")
            ))
        )
        listDrawer.add(
            Menu("Profile", listOf(
                Menu("Authentication"),
                Menu("Edit Profile Details"), Menu("Notificaitons"),
                Menu("Change Password"),
                Menu( "Get Level 2 Verified")
            ))
        )
        listDrawer.add(
            Menu("Support Center", listOf(
                Menu("FAQs"),
                Menu( "How-To Videos",HowToVideoActivity::class.java),
                Menu("More Support")
            ))
        )
        listDrawer.add(
            Menu("", listOf(
                Menu(getString(R.string.terms_condition)),
                Menu( getString(R.string.item_about)),
                Menu( getString(R.string.item_log_out))
            ))
        )
        bindingDrawer.listViewDrawer?.adapter = DrawerMenuAdapter(BaseActivity@this, listDrawer!!)
        bindingDrawer.listViewDrawer.setOnItemClickListener{ parent, view, position, id ->
            DrawerMenuAdapter(BaseActivity@this, listDrawer!!).onClickMenuDrawer(listDrawer.get(position))
        }
    }
}