package com.example.first_app.widgets.app_bar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.first_app.R

@Composable
fun HomeView() {
    val categories = listOf("Hits", "Workout", "Running", "TIGS", "yoga")
    val grouped = listOf<String>("New Release", "Top Rated", "Favorites").groupBy { it[0] }
    LazyColumn {
        grouped.forEach { (key, values) ->
            stickyHeader {
                Text(text = values.toString())
                LazyRow {
                    items(categories) { cat ->
                        BrowserItems(cat, R.drawable.baseline_library_add_24)
                    }
                }
            }
        }
    }
}

@Composable
fun BrowserItems(cat: String, drawable: Int) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .size(200.dp)
            .border(BorderStroke(3.dp, color = Color.DarkGray))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = cat)
            Image(painter = painterResource(id = drawable), contentDescription = cat)
        }
    }
}