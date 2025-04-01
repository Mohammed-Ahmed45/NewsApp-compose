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
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mohamed.newsapp.R
import com.mohamed.newsapp.api.ApiManager
import com.mohamed.newsapp.api.handleError
import com.mohamed.newsapp.api.response.ArticlesItem
import com.mohamed.newsapp.api.response.ArticlesResponse
import com.mohamed.newsapp.ui.ui.theme.Colors
import com.mohamed.newsapp.utils.ErrorDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsScreen(title: String)
{
    val decodedTitle = Uri.decode(title)
    val articlesItem = remember { mutableStateListOf<ArticlesItem>() }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableIntStateOf(R.string.empty) }
    LaunchedEffect(decodedTitle) {
        getDetails(decodedTitle, articlesItem, errorMessage, isLoading)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        if (articlesItem.isNotEmpty())
        {
            val article = articlesItem[0]
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
        ErrorDialog(errorState = errorMessage) {
            isLoading.value = true
            articlesItem.clear()
            getDetails(title, articlesItem, errorMessage, isLoading)
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
}

fun getDetails(
    title: String,
    articlesItem: SnapshotStateList<ArticlesItem>,
    errorMessage: MutableIntState,
    isLoading: MutableState<Boolean>
)
{
    ApiManager.getNewsService().getNewsSourcesSelected(title = title)
        .enqueue(object : Callback<ArticlesResponse>
        {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            )
            {

                isLoading.value = false
                val responseBody = response.body()
                if (!responseBody?.articles.isNullOrEmpty())
                {
                    articlesItem.clear()
                    articlesItem.addAll(responseBody!!.articles!!)
                }
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable)
            {
                isLoading.value = false
                errorMessage.intValue = handleError(t)

            }
        })
}

//@Preview
//@Composable
//private fun DetailPrev()
//{
//    DetailsScreen(title = "Mr")
//}