package com.mohamed.newsapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.mohamed.newsapp.R
import com.mohamed.newsapp.utils.AppDrawer
import com.mohamed.newsapp.utils.NewsTopAppBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun SettingsScreen(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavHostController
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(navController, drawerState)
        }
    )
    {
        Scaffold(
            topBar = {
                NewsTopAppBar(
                    title = stringResource(R.string.settings),
                    scope = scope,
                    drawerState = drawerState
                )
            }
        )
        { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) { }

        }
    }
}