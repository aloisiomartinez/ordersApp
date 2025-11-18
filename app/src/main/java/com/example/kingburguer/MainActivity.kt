package com.example.kingburguer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.collection.intSetOf
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.kingburguer.compose.KingBurguerApp
import com.example.kingburguer.compose.KingBurguerAppPreview
import com.example.kingburguer.compose.KingBurguerNavHost
import com.example.kingburguer.compose.Screen
import com.example.kingburguer.ui.theme.KingBurguerTheme
import com.example.kingburguer.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels {
        SplashViewModel.factory
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                Log.d("MainActivity", "Keep on!!")
                viewModel.hasSession.value == null
            }
        }

        setContent {
            KingBurguerTheme {
                val startState = viewModel.hasSession.collectAsState()
                Log.d("MainActivity", "Compose Started $startState")

                startState.value?.let { logged ->
                    val startDestination = if (logged) Screen.MAIN else Screen.LOGIN
                    Scaffold() { contentPadding ->
                        KingBurguerApp(
                            contentPadding = contentPadding,
                            startDestination = startDestination
                        )
                    }
                }
            }
        }
    }
}
