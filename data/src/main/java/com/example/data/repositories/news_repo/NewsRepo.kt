package com.example.data.repositories.news_repo

import com.example.data.api.response.ArticlesResponse
import com.example.data.api.response.SourcesResponse
import retrofit2.Response

interface NewsRepo {
    suspend fun getSources(categoryId: String): Response<SourcesResponse>
    suspend fun getArticles(sourcesId: String): Response<ArticlesResponse>
}