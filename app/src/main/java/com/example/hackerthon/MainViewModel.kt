package com.example.hackerthon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val onSearchEvent = MutableLiveData<Boolean>()
    val search = MutableLiveData<String>()

    fun onSearch(){
        onSearchEvent.value = true
    }
}