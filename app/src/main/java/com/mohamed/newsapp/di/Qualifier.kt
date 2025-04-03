package com.mohamed.newsapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsAuthInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsHttpLoggingInterceptor