package com.app.demo.rx.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance {

    companion object {
        private lateinit var retrofit: Retrofit
        private val mBaseUrl = "https://restcountries.eu/rest/v2/"
        fun getRetrofitInstance(): Retrofit {

                retrofit = Retrofit.Builder()
                        .baseUrl(mBaseUrl)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            return retrofit
        }
    }
}