package com.baltroid.apps.ext

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.showCall(phone: String) {
    val callIntentUri = Uri.parse("tel:$phone")
    val callIntent = Intent(Intent.ACTION_DIAL, callIntentUri)
    startActivity(callIntent)
}

fun Context.showMail(email: String) {
    val emailIntentUri = Uri.parse("mailto:$email")
    val emailIntent = Intent(Intent.ACTION_SENDTO, emailIntentUri)
    startActivity(emailIntent)
}

fun Context.showWeb(webLink: String) {
    val webIntentUri = Uri.parse(webLink)
    val webIntent = Intent(Intent.ACTION_VIEW, webIntentUri)
    startActivity(webIntent)
}
