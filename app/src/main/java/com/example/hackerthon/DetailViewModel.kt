package com.example.hackerthon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hackerthon.response.Comment
import com.example.hackerthon.response.Data
import io.reactivex.disposables.CompositeDisposable

class DetailViewModel: ViewModel() {
    val titleTv = MutableLiveData<String>()
    val discript = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val star = MutableLiveData<String>()

    private val comments = ArrayList<Comment>()
    val adapter = DetailAdapter(comments)
}