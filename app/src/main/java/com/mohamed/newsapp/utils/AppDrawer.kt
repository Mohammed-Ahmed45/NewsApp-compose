package com.mohamed.newsapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mohamed.newsapp.R
import com.mohamed.newsapp.navigation.Route
import com.mohamed.newsapp.ui.ui.theme.Colors
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    navController: NavHostController,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet(
        modifier = Modifier.width(250.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_news),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(Colors.Green)
                .clip(RoundedCornerShape(12.dp))
        )

        NavigationDrawerItem(
            label = { Text("Category") },
            selected = false,
            onClick = {
                scope.launch { drawerState.close() }
                navController.navigate(Route.CATEGORIES)
            }
        )

        NavigationDrawerItem(
            label = { Text("Settings") },
            selected = false,
            onClick = {
                scope.launch { drawerState.close() }
                navController.navigate(Route.SETTINGS_SCREEN)
            }
        )
    }
}
