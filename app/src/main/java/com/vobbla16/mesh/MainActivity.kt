package com.vobbla16.mesh

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.vobbla16.mesh.ui.screens.NavGraphs
import com.vobbla16.mesh.ui.theme.MeshTheme
import org.koin.androidx.compose.koinViewModel

val LocalMainVM = staticCompositionLocalOf {
    MainActivityViewModel()
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        setContent {
            MeshTheme {
                val navController = rememberNavController()

                val vm: MainActivityViewModel = koinViewModel()

                CompositionLocalProvider(LocalMainVM provides vm) {
                    MainScaffold(navController) {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
