package com.funa2chile.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.funa2chile.app.ui.navigation.AppNavGraph
import com.funa2chile.app.ui.theme.Funa2ChileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Funa2ChileTheme {
                AppNavGraph()
            }
        }
    }
}
