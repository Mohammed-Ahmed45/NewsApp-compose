package com.mohamed.newsapp.utils

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mohamed.newsapp.R
import com.mohamed.newsapp.api.response.ArticlesItem
import com.mohamed.newsapp.list.Category
import com.mohamed.newsapp.navigation.Route

@Composable
fun CategoryCard(category: Category, navHostController: NavController)
{
    Card(
        onClick = {
            navHostController.navigate("${Route.NEWS_SOURCES}/${category.categoryId}")
        },
        modifier = Modifier
            .size(width = 150.dp, height = 170.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp),

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(category.color),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = category.image),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(20.dp),
                text = category.text,
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArticlesCard(article: ArticlesItem, navHostController: NavController)
{
    Column(
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                navHostController.navigate("${Route.DETAILS_SCREEN}/${Uri.encode(article.title)}")
            },
    ) {
        GlideImage(
            model = article.urlToImage
                ?: Image(painterResource(id = R.drawable.ic_launcher_foreground), ""),
            contentDescription = "Article Image",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = article.source?.name ?: "Unknown",
            Modifier.padding(10.dp),
            fontSize = 18.sp,
            color = Color.Gray
        )

        Text(
            text = article.title ?: "No Title",
            Modifier.padding(10.dp),
            fontSize = 18.sp,
            color = Color.Black
        )

        Text(
            text = article.description ?: "No Description",
            modifier = Modifier.padding(10.dp),
            maxLines = 2,
            fontSize = 20.sp,
            softWrap = true,
            overflow = TextOverflow.Ellipsis
        )
    }
}
