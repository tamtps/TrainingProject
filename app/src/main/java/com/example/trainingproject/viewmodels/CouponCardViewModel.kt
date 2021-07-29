package com.example.trainingproject.viewmodels

import android.util.Log
import android.util.Size
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CouponCardViewModel : ViewModel() {
    private var couponListLiveData: MutableLiveData<CouponResponse> = MutableLiveData()

    fun getCouponListObserver(): MutableLiveData<CouponResponse> {
        return couponListLiveData
    }

    fun makeApiCall(token : String,
                    fkUser: String,
                    keyword: String,
                    filter: String,
                    pageIndex: String,
                    pageSize: String) {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient().instance.getCoupon(token, fkUser, keyword, filter, pageIndex, pageSize)
                .enqueue(object : Callback<CouponResponse> {
                    override fun onResponse(
                        call: Call<CouponResponse>,
                        response: Response<CouponResponse>
                    ) {
                        if (response.isSuccessful) {
                            couponListLiveData.postValue(response.body())
                        }

                    }

                    override fun onFailure(call: Call<CouponResponse>, t: Throwable) {
                        t.message?.let { Log.e("View model error", it) }
                    }

                })
        }
    }
}