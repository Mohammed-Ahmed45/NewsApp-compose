package com.mohamed.newsapp.list


import androidx.compose.ui.graphics.Color
import com.mohamed.newsapp.R
import com.mohamed.newsapp.ui.ui.theme.Colors


data class Category(
    val name: String,
    val image: Int,
    val categoryId: String,
    val text: String,
    val color: Color
)

val categories = listOf(
    Category(
        name = "sports",
        categoryId = "sports",
        image = R.drawable.img_sports,
        text = "Sports",
        color = Colors.Red
    ),
    Category(
        name = "technology",
        categoryId = "technology",
        image = R.drawable.img_politics,
        text = "Politics",
        color = Colors.Blue
    ),
    Category(
        name = "health",
        categoryId = "health",
        image = R.drawable.img_health,
        text = "Health",
        color = Colors.Purple
    ),
    Category(
        name = "business",
        categoryId = "business",
        image = R.drawable.img_bussines,
        text = "business",
        color = Colors.Orange
    ),
    Category(
        name = "science",
        categoryId = "science",
        image = R.drawable.img_science,
        text = "Science",
        color = Colors.Green
    ),
    Category(
        name = "general",
        categoryId = "general",
        image = R.drawable.img_environment,
        text = "Environment",
        color = Color.DarkGray
    ),

    )
