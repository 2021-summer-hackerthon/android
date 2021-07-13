package com.example.hackerthon

import com.example.hackerthon.response.Data
import com.example.hackerthon.response.Response
import io.reactivex.Single
import retrofit2.http.GET

interface Service {

    @GET("api/hotplace/five")
    fun getFiveData() : Single<retrofit2.Response<Response<Data>>>

}