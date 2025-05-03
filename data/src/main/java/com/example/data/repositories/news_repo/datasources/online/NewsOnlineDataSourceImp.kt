package com.example.data.repositories.news_repo.datasources.online

import com.example.data.api.response.ArticlesResponse
import com.example.data.api.response.SourcesResponse
import retrofit2.Response

interface NewsOnlineDataSourceImp {
    suspend fun getSources(categoryId: String): Response<SourcesResponse>
    suspend fun getArticles(sourceId: String): Response<ArticlesResponse>
}