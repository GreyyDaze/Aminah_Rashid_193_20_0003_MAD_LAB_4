package com.example.lab4tasks

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteColorTK1Screen() {
    val context = LocalContext.current.applicationContext
    val key = "favorite_color"
    var favoriteColor by remember { mutableStateOf("") }

    val sharedPreferences = context.getSharedPreferences("favorite_colors", Context.MODE_PRIVATE)

    fun saveFavoriteColor(color: String) {
        sharedPreferences.edit().putString(key, color).apply()
    }

    fun loadFavoriteColor() {
        favoriteColor = sharedPreferences.getString(key, "") ?: ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite Color",
            tint = MaterialTheme.colorScheme.primary
        )
        OutlinedTextField(
            value = favoriteColor,
            onValueChange = { newColor ->
                favoriteColor = newColor
            },
            label = { Text("Favorite Color") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            Button(onClick = { saveFavoriteColor(favoriteColor) }) {
                Text("Save")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { loadFavoriteColor() }) {
                Text("Load")
            }
        }
    }
}