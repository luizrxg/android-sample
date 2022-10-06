package com.defaultapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.defaultapp.ui.app.DefaultApp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultApp(calculateWindowSizeClass(this))
        }
    }
}

