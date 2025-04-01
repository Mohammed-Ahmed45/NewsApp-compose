package com.mohamed.newsapp.api

import com.mohamed.newsapp.api.response.ArticlesResponse
import com.mohamed.newsapp.api.response.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService
{
    @GET("/v2/top-headlines/sources")
    fun getSources(
        @Query("category") categoryId: String? = null,
    ): Call<SourcesResponse>

    @GET("/v2/everything")
    fun getNewsSourcesSelected(
        @Query("sources") selectedSourcesId: String? = null,
        @Query("q") title: String? = null,
        @Query("searchIn") searchIn: String = "title",
    ): Call<ArticlesResponse>

//    @GET ("/v2/everything")
//    fun getNewsDetails(
//
//    ):Call<ArticlesResponse>

}