package com.baltroid.apps

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.baltroid.apps.navigation.MqNavHost
import com.baltroid.designsystem.theme.MqTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaltroidActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MqTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MqNavHost()
                }
            }
        }
    }
}

