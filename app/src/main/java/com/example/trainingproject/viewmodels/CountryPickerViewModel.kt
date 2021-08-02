package com.example.trainingproject.viewmodels


import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.bases.BaseViewModel
import com.example.trainingproject.models.Country
import com.example.trainingproject.models.Response
import retrofit2.Call


class CountryPickerViewModel : BaseViewModel<Response<ArrayList<Country>>, CountryPickerViewModel>(CountryPickerViewModel::class.java) {


    override fun retrofitCall(): Call<Response<ArrayList<Country>>> {
        return RetrofitClient().instance.getCountries()
    }
}