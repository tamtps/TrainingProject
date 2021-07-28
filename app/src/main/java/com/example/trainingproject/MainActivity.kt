package com.example.trainingproject

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.databinding.ActivityMainBinding
import com.example.trainingproject.models.CouponResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var prefs : SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        var token = prefs.getString("token",null)

        getCouponAPI(token!!)
    }

    fun getCouponAPI(token : String){
        RetrofitClient().videoInstance.getCoupon(token, "1368", "", "-1", "1", "100")
            .enqueue(object : Callback<CouponResponse> {
                override fun onResponse(
                    call: Call<CouponResponse>,
                    response: Response<CouponResponse>
                ) {

                    Log.e("Response--------", response.body().toString())

//                    Toast.makeText(context, "Data: " +response.body()!!.status, Toast.LENGTH_LONG).show()
//                    listCoupon.addAll(response.body()!!.result)
//                    binding!!.listCoupon.adapter =  WalletCouponAdapter(requireContext(), listCoupon)
//                    binding!!.listCoupon.layoutManager = LinearLayoutManager(requireContext())
                }
                override fun onFailure(call: Call<CouponResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

            })
    }
}