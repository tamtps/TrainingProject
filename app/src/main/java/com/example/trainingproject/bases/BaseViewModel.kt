package com.example.trainingproject.bases

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseViewModel<T, VM : ViewModel>(viewModelClass: Class<VM>) :
    ViewModel() {
    protected var observingList: MutableLiveData<T> = MutableLiveData()
    open fun getListObserver(): MutableLiveData<T> {
        return observingList
    }

    protected abstract fun retrofitCall() : Call<T>

    open fun makeApiCall() {
        retrofitCall().enqueue(object: Callback<T>{
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    observingList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                t.message?.let { Log.e("View model error", it) }
            }

        })
    }

}