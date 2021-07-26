package com.example.trainingproject.screens

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingproject.R
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.components.HowToVideoAdapter
import com.example.trainingproject.databinding.ActivityHowToVideoBinding
import com.example.trainingproject.models.VideoResponse
import com.example.trainingproject.models.Videos
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HowToVideoActivity : AppCompatActivity() {
    var list : ArrayList<Videos> = ArrayList()

    lateinit var binding : ActivityHowToVideoBinding
    var string : String = "ggg"
//    val prefs : SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
//    val token: String? = prefs.getString("token", null)
    val token= "8914cb78-7348-4129-ae0e-e4acf9a4d4d4"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHowToVideoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onAppBarBack()
        onHomeBack()
        getVideoAPI()
    }

    fun getVideoAPI(){
        RetrofitClient().videoInstance.getVideo(token!!)
            .enqueue(object : Callback<VideoResponse>{
                override fun onResponse(
                    call: Call<VideoResponse>,
                    response: Response<VideoResponse>
                ) {
                    list.addAll(response.body()!!.result.content)
                    binding.listHowToVideos.adapter = HowToVideoAdapter(applicationContext, list)
                    binding.listHowToVideos.layoutManager = LinearLayoutManager(applicationContext)
                    binding.listHowToVideos.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
                }

                override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error" +t.message, Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun onAppBarBack() {
//        TODO: Back APPBAR press
    }

    private fun onHomeBack() {
//        TODO: home appbar press
    }
}
