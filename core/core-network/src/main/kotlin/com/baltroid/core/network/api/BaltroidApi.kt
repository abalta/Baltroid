package com.baltroid.core.network.api

import com.baltroid.core.network.api.service.HitReadsService
import com.baltroid.core.network.util.retrofit
import retrofit2.create

class BaltroidApi {
    val hitReadsService: HitReadsService by lazy { retrofit().create() }
}