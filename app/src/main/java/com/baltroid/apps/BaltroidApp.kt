package com.baltroid.apps

import android.app.Application
import android.provider.Settings
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val ONESIGNAL_APP_ID = "########-####-####-####-############"

@HiltAndroidApp
class BaltroidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        OneSignal.Debug.logLevel = LogLevel.VERBOSE
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)
        val udid = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID).toString()
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(true)
            OneSignal.login(udid)
        }
    }
}