package com.mohamed.newsapp.screen

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mohamed.newsapp.news.NewsViewModel
import com.mohamed.newsapp.ui.ui.theme.Colors
import com.mohamed.newsapp.utils.ErrorDialog


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsScreen(
    title: String,
    viewModel: NewsViewModel = hiltViewModel()
)
{
    val decodedTitle = Uri.decode(title)
    LaunchedEffect(decodedTitle) {
        viewModel.getDetails(decodedTitle)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        if (viewModel.articlesItem.isNotEmpty())
        {
            val article = viewModel.articlesItem[0]
            GlideImage(
                model = article.urlToImage ?: "",
                contentDescription = "Article Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = article.source?.name ?: "Unknown Source",
                Modifier.padding(top = 10.dp),
                fontSize = 18.sp,
                color = Color.Gray
            )

            Text(
                text = article.title ?: "Unknown Title",
                Modifier.padding(top = 10.dp),
                fontSize = 18.sp,
                color = Color.Gray
            )

            Text(
                text = article.description ?: "No Description Available",
                modifier = Modifier.padding(top = 10.dp),
                maxLines = 4,
                fontSize = 20.sp,
                softWrap = true,
                overflow = TextOverflow.Ellipsis
            )
        }
        ErrorDialog(errorState = viewModel.errorMessage) {
            viewModel.isLoading.value = true
            viewModel.articlesItem.clear()
            viewModel.getDetails(title)
        }

        if (viewModel.isLoading.value)
        {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Colors.green)
            }
        }
    }
}


//@Preview
//@Composable
//private fun DetailPrev()
//{
//    DetailsScreen(title = "Mr")
//}