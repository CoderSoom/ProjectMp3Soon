package com.example.recyclerviewpool.model

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitUtils {
    @JvmStatic
    fun <T> createRetrofit(baseLink: String, clazz: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(baseLink)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(clazz)
    }
}