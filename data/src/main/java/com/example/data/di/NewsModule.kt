package com.example.data.di

import com.example.data.repositories.news_repo.NewsRepo
import com.example.data.repositories.news_repo.NewsRepoImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepo(
        repoImpl: NewsRepoImp
    ): NewsRepo

//    @Binds
//    @Singleton
//    abstract fun bindNewsOnlineDataSource(
//        dataSourceImpl: NewsOnlineDataSource
//    ): NewsOnlineDataSourceImp
}
