package com.example.data.api.model

import com.example.data.api.response.ArticlesResponse
import com.example.data.api.response.SourcesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService
{
    @GET("/v2/top-headlines/sources")
    suspend fun getSources(@Query("category") categoryId: String? = null): Response<SourcesResponse>

    @GET("/v2/everything")
    suspend fun getNewsSourcesSelected(
        @Query("sources") selectedSourcesId: String? = null,
        @Query("q") title: String? = null,
        @Query("searchIn") searchIn: String = "title",
    ): Response<ArticlesResponse>

//    @GET ("/v2/everything")
//    fun getNewsDetails(
//
//    ):Call<ArticlesResponse>

}