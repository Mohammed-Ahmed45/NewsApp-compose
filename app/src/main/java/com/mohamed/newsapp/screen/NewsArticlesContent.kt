package com.mohamed.newsapp.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.mohamed.newsapp.api.ApiManager
import com.mohamed.newsapp.api.handleError
import com.mohamed.newsapp.api.response.ArticlesItem
import com.mohamed.newsapp.api.response.ArticlesResponse
import com.mohamed.newsapp.utils.ArticlesCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun NewsArticlesContent(selectedSourcesId: String, navController: NavController)
{
    val articlesList = remember { mutableStateListOf<ArticlesItem>() }
    val visibleItems = remember { mutableStateListOf<Boolean>() }

    LaunchedEffect(selectedSourcesId) {
        ApiManager.getNewsService().getNewsSourcesSelected(selectedSourcesId)
            .enqueue(object : Callback<ArticlesResponse>
            {
                override fun onResponse(
                    call: Call<ArticlesResponse>,
                    response: Response<ArticlesResponse>
                )
                {
                    val responseBody = response.body()
                    if (responseBody?.articles?.isNotEmpty() == true)
                    {
                        articlesList.clear()
                        visibleItems.clear()

                        responseBody.articles.forEach {
                            articlesList.add(it)
                            visibleItems.add(false)
                        }

                        CoroutineScope(Dispatchers.Main).launch {
                            responseBody.articles.forEachIndexed { index, _ ->
                                delay(index * 200L)
                                visibleItems[index] = true
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ArticlesResponse>, t: Throwable)
                {
                    handleError(t)
                }
            })
    }

    LazyColumn {
        itemsIndexed(articlesList) { index, article ->
            AnimatedVisibility(visible = visibleItems.getOrNull(index) == true) {
                ArticlesCard(article, navHostController = navController)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PrevNews()
{
    val selectedSourcesId = ""
// NewsArticlesSources(selectedSourcesId, navController = )
}
