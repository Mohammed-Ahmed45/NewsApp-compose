package com.mohamed.newsapp.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.mohamed.newsapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController)
{
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Route.CATEGORIES) {
            popUpTo("splash") { inclusive = true }
        }
    }

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.splash),
        contentDescription = "Splash Screen"
    )

}