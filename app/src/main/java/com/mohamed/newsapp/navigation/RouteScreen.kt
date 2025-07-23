package com.mohamed.newsapp.navigation

sealed class RouteScreen(val route: String)
{
    object DETAILS_SCREEN : RouteScreen(route = "details_screen")
    object DETAILS_Route : RouteScreen(route = "details_route")

}

//const val SPLASH = "splash"
//const val CATEGORIES = "categories"
//const val NEWS_SOURCES = "news_sources"