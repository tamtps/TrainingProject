package com.example.trainingproject.screens

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingproject.R
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseActivity
import com.example.trainingproject.bases.BaseDialog
import com.example.trainingproject.components.HowToVideoAdapter
import com.example.trainingproject.models.Content
import com.example.trainingproject.models.Response
import com.example.trainingproject.models.Videos
import retrofit2.Call
import retrofit2.Callback

class HowToVideoActivity : BaseActivity() {
    var list : ArrayList<Videos> = ArrayList()
    lateinit var progressCircular : ProgressBar
    lateinit var listHowToVideos : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leftIcon(R.drawable.nav_back)
        centerText(getString(R.string.appbar_how_to_video))
        rightIcon(R.drawable.btn_home_white)

        val prefs : SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        val deviceId = prefs.getString("deviceId","")

        progressCircular = findViewById(R.id.progress_circular)
        listHowToVideos = findViewById(R.id.list_how_to_videos)

        leftIcon.setOnClickListener(View.OnClickListener {
            finish()
        })

        rightIcon.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MainScreen::class.java))
            finish()
        })

        getVideoAPI("1")
    }

    override fun getBodyLayout(): Int {
        return R.layout.activity_how_to_video
    }

    override fun hasDrawer(): Boolean {return false }

    fun getVideoAPI(category: String){
        RetrofitClient().instance.getVideo(category)
            .enqueue(object : Callback<Response<Content>>{
                override fun onResponse(
                    call: Call<Response<Content>>,
                    response: retrofit2.Response<Response<Content>>
                ) {
                    progressCircular.visibility = View.INVISIBLE
                    list.addAll(response.body()!!.result.content)
                    listHowToVideos.adapter = HowToVideoAdapter(applicationContext, list)
                    listHowToVideos.layoutManager = LinearLayoutManager(applicationContext)
                    listHowToVideos.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
                }

                override fun onFailure(call: Call<Response<Content>>, t: Throwable) {
                    var dialog = BaseDialog(this@HowToVideoActivity)
                    dialog.setContentView()
                    dialog.errorDialog(t.message)
                }
            })
    }

}
