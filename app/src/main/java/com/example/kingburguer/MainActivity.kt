package com.example.kingburguer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.example.kingburguer.compose.KingBurguerApp
import com.example.kingburguer.ui.theme.KingBurguerTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            KingBurguerTheme {
                Scaffold() { contentPadding ->
                    KingBurguerApp(
                        contentPadding = contentPadding
                    )
                }
            }
        }
    }
}
