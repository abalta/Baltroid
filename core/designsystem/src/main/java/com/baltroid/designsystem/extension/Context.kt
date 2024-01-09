package com.baltroid.designsystem.extension

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.ColorInt
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent

fun Context.showCall(phone: String) {
    val callIntentUri = Uri.parse("tel:$phone")
    val callIntent = Intent(Intent.ACTION_DIAL, callIntentUri)
    startActivity(callIntent)
}

fun Context.launchCustomChromeTab(uri: String, @ColorInt toolbarColor: Int) {
    val customTabBarColor = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(toolbarColor).build()
    val customTabsIntent = CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(customTabBarColor)
        .build()

    customTabsIntent.launchUrl(this, Uri.parse(uri))
}

fun Context.showWeb(webLink: String) {
    val webIntentUri = Uri.parse(webLink)
    val webIntent = Intent(Intent.ACTION_VIEW, webIntentUri)
    startActivity(webIntent)
}

fun Context.showLocationOnMap(latLng: Pair<Double, Double>, mallName: String) {
    val gmmIntentUri =
        Uri.parse("geo:${latLng.first},${latLng.second}?z=10&q=${latLng.first},${latLng.second}($mallName)")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    mapIntent.resolveActivity(packageManager)?.let {
        startActivity(mapIntent)
    }
}

fun Context.showMail(email: String) {
    val emailIntentUri = Uri.parse("mailto:$email")
    val emailIntent = Intent(Intent.ACTION_SENDTO, emailIntentUri)
    startActivity(emailIntent)
}