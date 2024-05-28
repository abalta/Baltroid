package com.baltroid.core.common

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * General Preferences Helper class, used for storing preference values using the Preference API
 */
open class PreferencesHelper(context: Context) {

    companion object {
        private const val PREF_PACKAGE_NAME = "com.mekik.app"

        private const val PREF_KEY_USER_ACCESS_TOKEN = "user_access_token"

    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_PACKAGE_NAME, Context.MODE_PRIVATE)

    var userAccessToken: String? by StringPrefDelegate(PREF_KEY_USER_ACCESS_TOKEN, prefs)

    fun removeUserAccessToken() {
        prefs.edit().apply {
            remove(PREF_KEY_USER_ACCESS_TOKEN).apply()
        }
    }

}

class StringPrefDelegate(private val prefKey: String, private val pref: SharedPreferences) :
    ReadWriteProperty<PreferencesHelper, String?> {

    override fun getValue(thisRef: PreferencesHelper, property: KProperty<*>): String? =
        pref.getString(prefKey, "")

    override fun setValue(thisRef: PreferencesHelper, property: KProperty<*>, value: String?) {
        pref.edit().putString(prefKey, value).apply()
    }
}