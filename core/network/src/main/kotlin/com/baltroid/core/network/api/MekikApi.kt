package com.baltroid.core.network.api

import com.baltroid.core.common.PreferencesHelper
import com.baltroid.core.network.api.service.MekikService
import com.baltroid.core.network.util.retrofit
import retrofit2.create
import javax.inject.Inject

class MekikApi @Inject constructor(private val tokenProvider: PreferencesHelper) {
    val mekikService: MekikService by lazy { retrofit(tokenProvider).create() }
}