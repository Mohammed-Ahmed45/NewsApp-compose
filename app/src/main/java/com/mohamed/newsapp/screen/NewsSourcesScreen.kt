package com.mohamed.newsapp.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mohamed.newsapp.R
import com.mohamed.newsapp.api.ApiManager
import com.mohamed.newsapp.api.handleError
import com.mohamed.newsapp.api.response.SourcesItem
import com.mohamed.newsapp.api.response.SourcesResponse
import com.mohamed.newsapp.news.NewsViewModel
import com.mohamed.newsapp.ui.ui.theme.Colors
import com.mohamed.newsapp.utils.ErrorDialog
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("SuspiciousIndentation")
@Composable
fun NewsSourcesScreen(
    categoryId: String,
    viewModel: NewsViewModel = viewModel(),
    navController: NavController
)
{

    val sourcesList = remember { mutableStateListOf<SourcesItem>() }
    val navSelectedSourcesId = remember { mutableStateOf("") }
    var selectedTabPage by remember { mutableIntStateOf(0) }
    val errorMessage = remember { mutableIntStateOf(R.string.empty) }
    val isLoading = remember { mutableStateOf(true) }
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(categoryId) {
        getSources(categoryId, sourcesList, errorMessage, isLoading)
        isVisible = true
    }


    Column {
        if (sourcesList.isNotEmpty())
            AnimatedVisibility(
                visible = sourcesList.isNotEmpty(),
                enter = fadeIn() + slideInVertically { -it }
            ) {
                NewsScrollableTabRow(
                    sourcesList = sourcesList,
                    selectedTabIndex = selectedTabPage,
                    navSelectedSourcesId = navSelectedSourcesId,
                    onTabSelected = { page, id ->
                        selectedTabPage = page
                        navSelectedSourcesId.value = id
                    }
                )
            }

        AnimatedVisibility(
            visible = navSelectedSourcesId.value.isNotEmpty(),
            enter = fadeIn() + slideInVertically { -it }
        ) {
            NewsArticlesContent(
                selectedSourcesId = navSelectedSourcesId.value,
                navController = navController
            )
        }
    }

    ErrorDialog(errorState = errorMessage) {
        isLoading.value = true
        sourcesList.clear()
        getSources(categoryId, sourcesList, errorMessage, isLoading)
    }

    if (isLoading.value)
    {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Colors.green)
        }
    }

}

fun getSources(
    categoryId: String,
    sourcesList: SnapshotStateList<SourcesItem>,

    errorMessage: MutableIntState,
    isLoading: MutableState<Boolean>
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
                } else
                {
//                    handleError(response)
                }


            }

            override fun onFailure(p0: Call<SourcesResponse>, t: Throwable)
            {
                isLoading.value = false
                errorMessage.intValue = handleError(t)
            }

        })
}

@Composable
fun NewsScrollableTabRow(
    sourcesList: List<SourcesItem>,
    navSelectedSourcesId: MutableState<String>,
    selectedTabIndex: Int,
    onTabSelected: (index: Int, id: String) -> Unit
)
{
    val selectedModifier = Modifier
        .background(Colors.green, CircleShape)
        .padding(4.dp)
    val unSelectedModifier = Modifier
        .border(2.dp, Colors.green, CircleShape)
        .padding(4.dp)


    LaunchedEffect(Unit) {
        navSelectedSourcesId.value = sourcesList[0].id ?: ""
    }

    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        indicator = {}, divider = {}, edgePadding = 0.dp
    ) {
        sourcesList.forEachIndexed { index, sourcesItem ->
            var isVisible by remember { mutableStateOf(false) }


            LaunchedEffect(Unit) {
                delay(index * 150L)
                isVisible = true

            }

            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
            ) {
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        onTabSelected(index, sourcesItem.id ?: "")
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Colors.green,
                    modifier = if (selectedTabIndex == index)
                    {
                        selectedModifier
                    } else unSelectedModifier
                ) {
                    Text(text = sourcesItem.name ?: "")
                }
            }
        }
    }
}


