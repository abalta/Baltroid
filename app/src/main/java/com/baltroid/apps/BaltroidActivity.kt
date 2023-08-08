package com.baltroid.apps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.ui.main.MainUiState
import com.baltroid.apps.ui.main.MainViewModel
import com.baltroid.designsystem.component.Banner
import com.baltroid.designsystem.component.H3Title
import com.baltroid.designsystem.theme.MqTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaltroidActivity: ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        /*lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainState.collect {state ->
                    when(state) {
                        is MainUiState.Loading -> Log.i("DENEME", "LOADING")
                        is MainUiState.Success -> {
                            state.cityList.forEach {
                                Log.i("DENEME", "Kod: ${it.code} İsim: ${it.name}")
                            }
                        }
                    }

                }
            }
        }*/
        setContent {
            val mainState by viewModel.mainState.collectAsStateWithLifecycle()
            MqTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    when(mainState) {
                        is MainUiState.Success -> {
                            LazyColumn(contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp)) {
                                items((mainState as MainUiState.Success).cityList) { city ->
                                    H3Title("${city.name}")
                                    Spacer(modifier = Modifier.height(24.dp))
                                    if(city.malls.isEmpty()) {
                                        Banner()
                                    }
                                    Spacer(modifier = Modifier.height(34.dp))
                                }
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

}