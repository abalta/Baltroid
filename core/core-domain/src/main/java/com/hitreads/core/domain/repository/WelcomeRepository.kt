package com.hitreads.core.domain.repository

import com.baltroid.core.common.result.BaltroidResult
import com.hitreads.core.domain.model.WelcomeModel
import kotlinx.coroutines.flow.Flow

interface WelcomeRepository {
    fun getWelcomeList(): Flow<BaltroidResult<List<WelcomeModel>>>
}