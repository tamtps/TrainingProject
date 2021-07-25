package com.example.trainingproject.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.models.CountryResponse
import com.example.trainingproject.models.CountryResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordViewModel : ViewModel() {
    private var countryListLiveData : MutableLiveData<ArrayList<CountryResult>> = MutableLiveData()

    fun getCountryListObserver() : MutableLiveData<ArrayList<CountryResult>> {
        return countryListLiveData
    }

    fun makeApiCall() {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient().instance.getCountries()
                .enqueue(object : Callback<CountryResponse> {
                    override fun onResponse(
                        call: Call<CountryResponse>,
                        response: Response<CountryResponse>
                    ) {
                        if (response.isSuccessful && !response.body()?.result.isNullOrEmpty())
                            countryListLiveData.postValue(response.body()?.result)
                    }

                    override fun onFailure(call: Call<CountryResponse>, t: Throwable) {
                        t.message?.let { Log.e("View model error", it) }
                    }

                })
        }
    }
}