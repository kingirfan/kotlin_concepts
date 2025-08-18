package com.example.first_app.widgets.app_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Colors
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun LibraryScreen() {
    LazyColumn() {
        items(libraries) { libraries ->
            LibItem(libraries)
        }
    }
}

@Composable
fun LibItem(lib: Lib) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Icon(
                    painter = painterResource(id = lib.icon),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    contentDescription = lib.name
                )
                Text(text = lib.name)
            }
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = lib.name)
        }
        Divider(color = Color.DarkGray)
    }
}