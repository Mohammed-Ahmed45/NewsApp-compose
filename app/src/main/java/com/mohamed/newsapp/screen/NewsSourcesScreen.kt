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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mohamed.newsapp.news.NewsViewModel
import com.mohamed.newsapp.ui.ui.theme.Colors
import com.mohamed.newsapp.utils.ErrorDialog
import kotlinx.coroutines.delay


@SuppressLint("SuspiciousIndentation")
@Composable
fun NewsSourcesScreen(
    categoryId: String,
    viewModel: NewsViewModel = hiltViewModel(),
    navController: NavController
)
{
    LaunchedEffect(categoryId) {
        viewModel.getSources(categoryId)
        viewModel.isVisible.value = true
    }


    Column {
        if (viewModel.sourcesList.isNotEmpty())
            AnimatedVisibility(
                visible = viewModel.sourcesList.isNotEmpty(),
                enter = fadeIn() + slideInVertically { -it }
            ) {
                NewsScrollableTabRow(
                    selectedTabIndex = viewModel.selectedTabPage.intValue,
                    onTabSelected = { page, id ->
                        viewModel.selectedTabPage.intValue = page
                        viewModel.navSelectedSourcesId.value = id
                    }
                )
            }

        AnimatedVisibility(
            visible = viewModel.navSelectedSourcesId.value.isNotEmpty(),
            enter = fadeIn() + slideInVertically { -it }
        ) {

            NewsArticlesContent(
                selectedSourcesId = viewModel.navSelectedSourcesId,
                navController = navController
            )
        }
    }

    ErrorDialog(errorState = viewModel.errorMessage) {
        viewModel.isLoading.value = true
        viewModel.sourcesList.clear()
        viewModel.getSources(categoryId)
    }

    if (viewModel.isLoading.value)
    {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Colors.Green)
        }
    }

}


@Composable
fun NewsScrollableTabRow(
    viewModel: NewsViewModel = hiltViewModel(),
    selectedTabIndex: Int,
    onTabSelected: (index: Int, id: String) -> Unit
) {
    val selectedModifier = Modifier
        .background(Colors.Green, CircleShape)
        .padding(4.dp)
    val unSelectedModifier = Modifier
        .border(2.dp, Colors.Green, CircleShape)
        .padding(4.dp)


    LaunchedEffect(Unit) {
        if (viewModel.sourcesList.isNotEmpty() && viewModel.selectedTabPage.intValue == 0) {
            viewModel.navSelectedSourcesId.value = viewModel.sourcesList.firstOrNull()?.id ?: ""
        } else {
            viewModel.selectedTabPage.intValue = selectedTabIndex

        }

    }

    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        indicator = {}, divider = {}, edgePadding = 0.dp

    ) {
        viewModel.sourcesList.forEachIndexed { index, sourcesItem ->
            var isVisible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(index * 150L)
                isVisible = true

            }

            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
            ) {
                Box(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            if (index < viewModel.sourcesList.size) {
                                onTabSelected(index, sourcesItem.id ?: "")
                            }
                        },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Colors.Green,
                        modifier =
                            (if (selectedTabIndex == index) {
                                selectedModifier
                            } else unSelectedModifier
                                    ).padding(10.dp)

                    ) {
                        Text(text = sourcesItem.name ?: "")
                    }
                }
            }
        }
    }
}


