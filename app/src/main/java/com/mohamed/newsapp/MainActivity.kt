package com.mohamed.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mohamed.newsapp.list.categories
import com.mohamed.newsapp.navigation.Nav
import com.mohamed.newsapp.ui.theme.NewsAppTheme
import com.mohamed.newsapp.utils.AppDrawer
import com.mohamed.newsapp.utils.CategoryCard
import com.mohamed.newsapp.utils.NewsTopAppBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Nav()
            }
        }
    }
}


@Composable
fun CategoryScreen(navHostController: NavHostController)
{
    val drawerState= rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { visible = true }

    ModalNavigationDrawer(
        drawerState=drawerState,
        drawerContent = {
            AppDrawer(navHostController,drawerState)
        }
    ) {
        Scaffold(
            topBar = {
                NewsTopAppBar(title = stringResource(R.string.newsApp),scope = scope,drawerState=drawerState)
            }
        )
        { paddingValues ->

            Column(modifier = Modifier.padding(paddingValues)) {
                Text(
                    text = "Welcome to News App\nSelect your favorite news",
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center
                )

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(categories) { index, categoryItem ->
                        var itemVisible by remember { mutableStateOf(false) }

                        LaunchedEffect(Unit) {
                            delay(index * 100L)
                            itemVisible = true
                        }

                        AnimatedVisibility(
                            visible = itemVisible,
                            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                                initialOffsetY = { it / 2 })
                        ) {
                            CategoryCard(categoryItem, navHostController)
                        }
                    }
                }
            }
        }

    }
}