package com.baltroid.apps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.baltroid.apps.navigation.BottomBar
import com.baltroid.apps.navigation.BottomNavGraph
import com.baltroid.apps.navigation.RootNavGraph
import com.baltroid.designsystem.component.TopBar
import com.baltroid.designsystem.theme.MekikTheme
import dagger.hilt.android.AndroidEntryPoint
import io.sanghun.compose.video.cache.VideoPlayerCacheManager

@AndroidEntryPoint
class BaltroidActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        VideoPlayerCacheManager.initialize(this, 1024 * 1024 * 1024)

        setContent {
            val navController = rememberNavController()
            val isDarkMode = isSystemInDarkTheme()
            val whiteScrim = android.graphics.Color.argb(0xFF, 0xFF, 0xFF, 0xFF)
            val context = LocalContext.current as ComponentActivity

            DisposableEffect(isDarkMode) {
                context.window.statusBarColor = if (isDarkMode) {
                    whiteScrim
                } else {
                    whiteScrim
                }
                context.window.navigationBarColor = if (isDarkMode) {
                    whiteScrim
                } else {
                    whiteScrim
                }

                WindowCompat.getInsetsController(context.window, context.window.decorView).apply {
                    isAppearanceLightStatusBars = !isDarkMode
                    isAppearanceLightNavigationBars = !isDarkMode
                }

                onDispose { }
            }

            MekikTheme {
                RootNavGraph(navController = navController)
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainActivity() {
    val navController = rememberNavController()
    MekikTheme {
        RootNavGraph(navController = navController)
    }
}
