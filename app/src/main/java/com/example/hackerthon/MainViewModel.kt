package com.example.hackerthon

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hackerthon.response.Data
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    val onSearchEvent = MutableLiveData<Boolean>()
    val search = MutableLiveData<String>()

    val compositeDisposable = CompositeDisposable()
    private val dataList = ArrayList<Data>()
    val adapter = Adapter(dataList)

    fun onSearch() {
        onSearchEvent.value = true
    }

    fun getByStarData() {
        compositeDisposable.add(
            Retrofit.service.getByStarData("star").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({

                    dataList.clear()
                    it.body()?.data?.let { it1 -> dataList.addAll(it1) }
                    adapter.notifyDataSetChanged()

                }, {
                    Log.e("오류", it.message.toString())
                })
        )
    }
}