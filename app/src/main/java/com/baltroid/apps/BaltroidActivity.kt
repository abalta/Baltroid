package com.baltroid.apps

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.baltroid.ui.navigation.HitReadsNavHost
import com.baltroid.ui.theme.HitReadsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaltroidActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HitReadsTheme {
                HitReadsNavHost(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}