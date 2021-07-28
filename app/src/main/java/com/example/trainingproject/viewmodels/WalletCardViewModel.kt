package com.example.trainingproject.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.models.Account
import com.example.trainingproject.models.CountryResponse
import com.example.trainingproject.models.WalletCardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WalletCardViewModel : ViewModel() {
    private var accountListLiveData: MutableLiveData<WalletCardResponse> = MutableLiveData()

    fun getAccountListObserver() : MutableLiveData<WalletCardResponse> {
        return accountListLiveData
    }

    fun makeApiCall(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient().walletCardInstance.getWalletCard(token, "All", "", "", "")
                .enqueue(object : Callback<WalletCardResponse>{
                    override fun onResponse(
                        call: Call<WalletCardResponse>,
                        response: Response<WalletCardResponse>
                    ) {
                        if (response.isSuccessful ){
                            accountListLiveData.postValue(response.body())
                            Log.d("walletCardSuccessful",response.body().toString())
                        }
                        else {
                            Log.d("walletCardFailed",response.body().toString())
                        }

                    }

                    override fun onFailure(call: Call<WalletCardResponse>, t: Throwable) {
                        t.message?.let { Log.e("View model error", it) }
                    }

                })
        }
    }
}