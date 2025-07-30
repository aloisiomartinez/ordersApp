package com.example.kingburguer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kingburguer.compose.KingBurguerApp
import com.example.kingburguer.ui.theme.KingBurguerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KingBurguerTheme {
                KingBurguerApp()
            }
        }
    }
}
