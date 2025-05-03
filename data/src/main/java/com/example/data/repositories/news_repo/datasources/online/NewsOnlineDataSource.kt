package com.example.data.repositories.news_repo.datasources.online

import com.example.data.api.model.ApiService
import com.example.data.api.response.ArticlesResponse
import com.example.data.api.response.SourcesResponse
import retrofit2.Response


import javax.inject.Inject


class NewsOnlineDataSource @Inject constructor(
    private val apiService: ApiService
) : NewsOnlineDataSourceImp {

    //    suspend fun getSources(categoryId:String): Response<SourcesResponse> {
//       return apiService.getSources(categoryId)
//    }
//
//    suspend fun getArticles(sourcesId:String): Response<ArticlesResponse> {
//        return apiService.getNewsSourcesSelected(sourcesId)
//    }
    override suspend fun getSources(categoryId: String): Response<SourcesResponse> {
        val response = apiService.getSources(categoryId)
        return response
    }

    override suspend fun getArticles(sourceId: String): Response<ArticlesResponse> {
        val response = apiService.getNewsSourcesSelected(sourceId)
        return response
    }
}