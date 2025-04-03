package com.mohamed.newsapp.api

import android.util.Log
import com.mohamed.newsapp.di.NewsAuthInterceptor
import com.mohamed.newsapp.di.NewsHttpLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// 1. Declare Module
// 2. inject Dependencies

@Module
@InstallIn(SingletonComponent::class)
object ApiManager
{

    @Provides
    @Singleton
    fun providesBaseUrl(): String = "https://newsapi.org/"

    @Provides
    @Singleton
    @NewsHttpLoggingInterceptor
    fun provideHttpLoggingInterceptor(): Interceptor
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

    @Provides
    @Singleton
    @NewsAuthInterceptor
    fun authApiKeyInterceptor(): Interceptor
    {
        return AuthApiKeyInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @NewsAuthInterceptor authApiKeyInterceptor: Interceptor,
        @NewsHttpLoggingInterceptor httpLoggingInterceptor: Interceptor
    ): OkHttpClient
    {
        return OkHttpClient.Builder()
            .addInterceptor(authApiKeyInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    }
//
//    init
//    {
//        initRetrofit()
//    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory
    {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun initRetrofit(
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,

        ): Retrofit
    {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun getNewsService(retrofit: Retrofit): ApiService
    {
        return retrofit.create(ApiService::class.java)
    }
}