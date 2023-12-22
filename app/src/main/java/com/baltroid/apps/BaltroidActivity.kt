package com.baltroid.apps

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import com.baltroid.apps.navigation.MqNavHost
import com.baltroid.apps.ui.main.MainUiState
import com.baltroid.apps.ui.main.MainViewModel
import com.baltroid.designsystem.component.Banner
import com.baltroid.designsystem.component.CardMedium
import com.baltroid.designsystem.component.H3Title
import com.baltroid.designsystem.theme.MqTheme
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BaltroidActivity: ComponentActivity() {

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

fun Context.showLocationOnMap(latLng: Pair<Double, Double>, mallName: String) {
    val gmmIntentUri = Uri.parse("geo:${latLng.first},${latLng.second}?z=10&q=${latLng.first},${latLng.second}($mallName)")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    mapIntent.resolveActivity(packageManager)?.let {
        startActivity(mapIntent)
    }
}

fun Context.showCall(phone: String) {
    val callIntentUri = Uri.parse("tel:$phone")
    val callIntent = Intent(Intent.ACTION_DIAL, callIntentUri)
    startActivity(callIntent)
}

fun Context.showWeb(webLink: String) {
    val webIntentUri = Uri.parse(webLink)
    val webIntent = Intent(Intent.ACTION_VIEW, webIntentUri)
    startActivity(webIntent)
}

fun Context.showMail(email: String) {
    val emailIntentUri = Uri.parse("mailto:$email")
    val emailIntent = Intent(Intent.ACTION_SENDTO, emailIntentUri)
    startActivity(emailIntent)
}