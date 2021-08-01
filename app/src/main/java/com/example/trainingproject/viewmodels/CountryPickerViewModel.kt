package com.example.trainingproject.viewmodels


import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseViewModel
import com.example.trainingproject.models.Country
import com.example.trainingproject.models.Response
import retrofit2.Call


class CountryPickerViewModel : BaseViewModel<Response<Country>, CountryPickerViewModel>(CountryPickerViewModel::class.java) {

//    fun makeApiCall() {
//        viewModelScope.launch(Dispatchers.IO) {
//            RetrofitClient().instance.getCountries()
//                .enqueue(object : Callback<CountryResponse> {
//                    override fun onResponse(
//                        call: Call<CountryResponse>,
//                        response: Response<CountryResponse>
//                    ) {
//                        if (response.isSuccessful && !response.body()?.result.isNullOrEmpty())
//                            countryListLiveData.postValue(response.body()?.result)
//                    }
//
//                    override fun onFailure(call: Call<CountryResponse>, t: Throwable) {
//                        t.message?.let { Log.e("View model error", it) }
//                    }
//
//                })
//        }
//    }

    override fun retrofitCall(): Call<Response<Country>> {
        return RetrofitClient().instance.getCountries()
    }
}