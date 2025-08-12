package com.example.kingburguer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.core.view.WindowCompat
import com.example.kingburguer.compose.KingBurguerApp
import com.example.kingburguer.ui.theme.KingBurguerTheme


class MainActivity : ComponentActivity() {
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
