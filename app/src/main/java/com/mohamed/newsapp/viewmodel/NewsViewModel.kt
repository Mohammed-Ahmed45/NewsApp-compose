package com.mohamed.newsapp.viewmodel

//import com.mohamed.newsapp.data.api.model.ApiService
//import com.mohamed.newsapp.data.api.model.handleError
//import com.mohamed.newsapp.data.api.repositories.news_repo.NewsRepoImp
//import com.mohamed.newsapp.data.api.response.ArticlesItem
//import com.mohamed.newsapp.data.api.response.SourcesItem
import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.model.ApiService
import com.example.data.api.model.handleError
import com.example.data.api.response.ArticlesItem
import com.example.data.api.response.SourcesItem
import com.example.data.repositories.news_repo.NewsRepo
import com.mohamed.newsapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val apiService: ApiService,
    private val newsRepo: NewsRepo
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
            try {
                val response = newsRepo.getSources(categoryId)
                isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.sources?.isNotEmpty() == true) {
                        sourcesList.clear()
                        sourcesList.addAll(responseBody.sources!!)
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
                val response = newsRepo.getArticles(sourcesId = selectedSourcesId.value)
                val responseBody = response.body()
//                sourcesList.clear()

                if (responseBody?.articles?.isNotEmpty() == true)
                {
                    articlesItem.clear()
                    visibleItems.clear()

                    responseBody.articles!!.forEach {
                        articlesItem.add(it)
                        visibleItems.add(false)
                    }

                    CoroutineScope(Dispatchers.Main).launch {
                        responseBody.articles!!.forEachIndexed { index, _ ->
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