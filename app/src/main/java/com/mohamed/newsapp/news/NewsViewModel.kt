package com.mohamed.newsapp.news

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.newsapp.R
import com.mohamed.newsapp.api.model.ApiService
import com.mohamed.newsapp.api.model.handleError
import com.mohamed.newsapp.api.response.ArticlesItem
import com.mohamed.newsapp.api.response.SourcesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel()
{
    val sourcesList = mutableStateListOf<SourcesItem>()
    val navSelectedSourcesId = mutableStateOf("")
    var selectedTabPage = mutableIntStateOf(0)
    val errorMessage = mutableIntStateOf(R.string.empty)
    val isLoading = mutableStateOf(true)
    var isVisible = mutableStateOf(false)
    val articlesItem = mutableStateListOf<ArticlesItem>()

    @SuppressLint("SuspiciousIndentation")
    fun getSources(
        categoryId: String,
    )
    {
        viewModelScope.launch(Dispatchers.IO) {
            try
            {
                val response = apiService.getSources(categoryId = categoryId)
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


            } catch (e: Exception)
            {

                isLoading.value = false
                errorMessage.intValue = handleError(e)
            }
        }

    }

    // in view model do not use remember because is a state

    val visibleItems = mutableStateListOf<Boolean>()
    fun getNewsArticlesBySource(
        selectedSourcesId: MutableState<String>,
    )
    {

        viewModelScope.launch(Dispatchers.IO) {
            try
            {
                val response = apiService.getNewsSourcesSelected(title = selectedSourcesId.value)
                val responseBody = response.body()
                if (responseBody?.articles?.isNotEmpty() == true)
                {
                    articlesItem.clear()
                    visibleItems.clear()

                    responseBody.articles.forEach {
                        articlesItem.add(it)
                        visibleItems.add(false)
                    }

                    CoroutineScope(Dispatchers.Main).launch {
                        responseBody.articles.forEachIndexed { index, _ ->
                            delay(index * 200L)
                            if (index < visibleItems.size)
                            {
                                visibleItems[index] = true
                            }
                        }
                    }
                }
            } catch (e: Exception)
            {
                isLoading.value = false
                errorMessage.intValue = handleError(e)
            }
        }
    }


    fun getDetails(
        title: String,
    )
    {

        viewModelScope.launch(Dispatchers.IO) {
            try
            {
                val response = apiService.getNewsSourcesSelected(title = title)

                isLoading.value = false
                if (response.isSuccessful)
                {
                    val responseBody = response.body()
                    if (!responseBody?.articles.isNullOrEmpty())
                    {
                        articlesItem.clear()
                        articlesItem.addAll(responseBody!!.articles!!)
                    }
                }
            } catch (e: Exception)
            {
                isLoading.value = false
                errorMessage.intValue = handleError(e)
            }

        }
    }

}