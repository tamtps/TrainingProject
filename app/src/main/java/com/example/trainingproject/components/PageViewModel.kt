package com.example.trainingproject.components

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()

    @Bindable
    var _search = MutableLiveData<String>()



//    val _search : ObservableField<String> = ObservableField<String>()

    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

//    val searchText : LiveData<String> = Transformations.map(_search){
//        "Search $it"
//    }

    fun setIndex(index: Int) {
        _index.value = index
    }

//    fun setSearch(search: String){
//        _search.value = search
//    }



}