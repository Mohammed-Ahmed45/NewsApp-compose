package com.example.data.repositories.news_repo

import com.example.data.api.response.ArticlesResponse
import com.example.data.api.response.SourcesResponse
import com.example.data.repositories.news_repo.datasources.online.NewsOnlineDataSource
import retrofit2.Response
import javax.inject.Inject

class NewsRepoImp @Inject constructor(
//    private val offlineDataSource: NewsOfflineDataSource,
    private val onlineDataSource: NewsOnlineDataSource,
//    private val apiService: ApiService
) : NewsRepo {


    //    suspend fun getSources(categoryId: String): Response<SourcesResponse> {
//        val sourcesResponse = onlineDataSource.getSources(categoryId)
//        return sourcesResponse
//    }
//
//    suspend fun getArticles(sourcesId: String): Response<ArticlesResponse> {
//        val articlesResponse = onlineDataSource.getArticles(sourcesId)
//        return articlesResponse
//    }
    override suspend fun getSources(categoryId: String): Response<SourcesResponse> {
        val response = onlineDataSource.getSources(categoryId)
        return response
    }

    override suspend fun getArticles(sourcesId: String): Response<ArticlesResponse> {
        val response = onlineDataSource.getArticles(sourcesId)
        return response
    }
}