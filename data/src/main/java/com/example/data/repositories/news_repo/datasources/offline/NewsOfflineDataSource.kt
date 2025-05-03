package com.example.data.repositories.news_repo.datasources.offline


import com.example.data.api.response.ArticlesResponse
import com.example.data.api.response.SourcesResponse

class NewsOfflineDataSource {


    suspend fun getSources(categoryId: String): SourcesResponse {

        return SourcesResponse()
    }

    suspend fun getArticles(sourcesId: String): ArticlesResponse {
        return ArticlesResponse()
    }
}