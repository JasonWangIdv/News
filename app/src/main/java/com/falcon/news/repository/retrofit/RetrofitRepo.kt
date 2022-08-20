package com.falcon.news.repository.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRepo private constructor() {

    companion object {
        private const val ITUNES_DOMAIN = "https://static.mixerbox.com"

        private val retrofitRepoInstance = RetrofitRepo()

        fun getApis() = retrofitRepoInstance.apis
    }

    private val retrofit: Retrofit
    private val apis: Apis

    init {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()

        retrofit = Retrofit.Builder()
            .baseUrl(ITUNES_DOMAIN)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apis = retrofit.create(Apis::class.java)
    }

}