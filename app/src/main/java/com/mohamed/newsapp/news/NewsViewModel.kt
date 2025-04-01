package com.mohamed.newsapp.news

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mohamed.newsapp.R
import com.mohamed.newsapp.api.ApiManager
import com.mohamed.newsapp.api.handleError
import com.mohamed.newsapp.api.response.ArticlesItem
import com.mohamed.newsapp.api.response.ArticlesResponse
import com.mohamed.newsapp.api.response.SourcesItem
import com.mohamed.newsapp.api.response.SourcesResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel()
{
    val sourcesList = mutableStateListOf<SourcesItem>()

    //was var
    val navSelectedSourcesId = mutableStateOf("")

    var selectedTabPage = mutableIntStateOf(0)
    val errorMessage = mutableIntStateOf(R.string.empty)
    val isLoading = mutableStateOf(true)
    var isVisible = mutableStateOf(false)
    fun getSources(
        categoryId: String,
//        sourcesList: SnapshotStateList<SourcesItem>,
//
//        errorMessage: MutableIntState,
//        isLoading: MutableState<Boolean>
    )
    {
        ApiManager.getNewsService().getSources(categoryId = categoryId)
            .enqueue(object : Callback<SourcesResponse>
            {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                )
                {
                    isLoading.value = false
//                    response.body()?.sources?.let { sourcesList.addAll(it) }
                    if (response.isSuccessful)
                    {
                        val responseBody = response.body()
                        if (responseBody?.sources?.isNotEmpty() == true)
                        {
                            sourcesList.clear()
                            sourcesList.addAll(responseBody.sources)

                        }
                    }


                }

                override fun onFailure(p0: Call<SourcesResponse>, t: Throwable)
                {
                    isLoading.value = false
                    errorMessage.intValue = handleError(t)
                }

            })
    }

    // in view model do not use remember because is a state
    val articlesList = mutableStateListOf<ArticlesItem>()
    val visibleItems = mutableStateListOf<Boolean>()
    val selectedSourcesId = mutableStateOf("")

    fun getNewsArticlesBySource()
    {


        ApiManager.getNewsService().getNewsSourcesSelected(selectedSourcesId.value)
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
                    isLoading.value = false
                    errorMessage.intValue = handleError(t)
                }
            })
    }
}