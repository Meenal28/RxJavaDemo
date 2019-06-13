package com.app.demo.rx.network

import com.app.demo.rx.model.CountryModel
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("all")
    fun getCountriesList() : Observable<MutableList<CountryModel>>
}