package com.mohamed.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mohamed.newsapp.CategoryScreen
import com.mohamed.newsapp.screen.DetailsScreen
import com.mohamed.newsapp.screen.NewsSourcesScreen

object Route
{
    const val SPLASH = "splash"
    const val CATEGORIES = "categories"
    const val NEWS_SOURCES = "news_sources"
    const val DETAILS_SCREEN = "details_screen"
}

@Composable
fun Nav()
{
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.SPLASH) {
        composable(Route.SPLASH) { SplashScreen(navController) }
        composable(Route.CATEGORIES) { CategoryScreen(navController) }

        composable(
            route = "${Route.NEWS_SOURCES}/{categoryId}",
            arguments = listOf(navArgument(name = "categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            NewsSourcesScreen(categoryId = categoryId!!, navController = navController)
        }

        composable(
            route = "${Route.DETAILS_SCREEN}/{title}",
            arguments = listOf(navArgument(name = "title") { type = NavType.StringType })
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: "No Title"
            DetailsScreen(title)
        }
    }
}
