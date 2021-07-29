package com.example.trainingproject.viewmodels

import android.util.Log
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


class GiftCardViewModel : ViewModel() {
    private var transListLiveData: MutableLiveData<TransactionDisplay> = MutableLiveData()

    fun getTransListObserver() : MutableLiveData<TransactionDisplay> {
        return transListLiveData
    }

    fun makeApiCall(token: String,
                    receiverUserId: String,
                    keyword: String,
                    pageIndex: String,
                    pageSize: String,
                    filter: String) {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient().instance.getGiftCard(token, receiverUserId, keyword,
                pageIndex, pageSize,filter)
                .enqueue(object : Callback<GiftCardResponse>{
                    override fun onResponse(
                        call: Call<GiftCardResponse>,
                        response: Response<GiftCardResponse>
                    ) {
                        if (response.isSuccessful ){
                            transListLiveData.postValue(response.body()!!.result)
                        }

                    }

                    override fun onFailure(call: Call<GiftCardResponse>, t: Throwable) {
                        t.message?.let { Log.e("View model error", it) }
                    }

                })
        }
    }
}