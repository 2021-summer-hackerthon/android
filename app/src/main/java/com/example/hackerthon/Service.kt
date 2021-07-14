package com.example.hackerthon

import com.example.hackerthon.response.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("api/hotplace/all")
    fun getByStarData(
        @Query("option") star: String
    ) : Single<retrofit2.Response<Response>>

    @GET("api/hotplace/search")
    fun getSearchData(
        @Query("keyword") keyword: String
    ): Single<retrofit2.Response<Response>>
}