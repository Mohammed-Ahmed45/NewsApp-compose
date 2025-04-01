package com.mohamed.newsapp.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager
{
    val BASE_URL = "https://newsapi.org/"
    lateinit var retrofit: Retrofit


    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor
    {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
            Log.e("Api", message)
        }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
//    private fun provideOkHttpClient():OkHttpClient
//    {
//        val okHttpClient=OkHttpClient.Builder()
//            .addInterceptor(provideHttpLoggingInterceptor())
//            .build()
//    }

    private fun provideOkHttpClient(): OkHttpClient
    {
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(AuthApiKeyInterceptor())
            .addInterceptor(provideHttpLoggingInterceptor())
            .build()
        return okHttpClient
    }

    init
    {
        initRetrofit()
    }

    fun initRetrofit()
    {

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
    }

    fun getNewsService(): ApiService
    {
        return retrofit.create(ApiService::class.java)
    }
}