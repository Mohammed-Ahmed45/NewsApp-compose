package com.mohamed.newsapp.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthApiKeyInterceptor : Interceptor
{
    val authorization = "Authorization"
    val apiKey = "397c92fdf44e4c14aaa358ea0e255765"
    override fun intercept(chain: Interceptor.Chain): Response
    {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader(authorization, apiKey)
        return chain.proceed(requestBuilder.build())
    }


}