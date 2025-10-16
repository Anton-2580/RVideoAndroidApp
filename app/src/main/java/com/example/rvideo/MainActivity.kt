package com.example.rvideo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.rvideo.navigation.NavigationRoot
import common.ui.theme.RVideoTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Timber.Forest.plant(Timber.DebugTree())
        }

        setContent {
            val navController = rememberNavController()

            RVideoTheme(darkTheme = true, dynamicColor = false) {
                Scaffold { innerPadding ->
                    NavigationRoot(
                        navController = navController,
                        padding = innerPadding,
                    )
                }
            }
        }
    }
}
