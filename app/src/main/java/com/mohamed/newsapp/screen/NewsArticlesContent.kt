package com.mohamed.newsapp.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mohamed.newsapp.news.NewsViewModel
import com.mohamed.newsapp.utils.ArticlesCard


@Composable
fun NewsArticlesContent(
    viewModel: NewsViewModel = hiltViewModel(),
    selectedSourcesId: MutableState<String>,
    navController: NavController
)
{

    LaunchedEffect(selectedSourcesId.value) {
        viewModel.getNewsArticlesBySource(selectedSourcesId = selectedSourcesId)

    }

    LazyColumn {
        itemsIndexed(viewModel.articlesItem) { index, article ->
            AnimatedVisibility(visible = viewModel.visibleItems.getOrNull(index) == true) {
                ArticlesCard(article, navHostController = navController)
            }
        }
    }
}

