package com.example.lab4tasks

import ImageGalleryTK4
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.lab4tasks.ui.theme.Lab4tasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4tasksTheme {
//                FavoriteColorTK1Screen()
//                CounterTK2Screen()
//                NoteTakingAppTK3()
                ImageGalleryTK4()
            }
        }
    }
}
