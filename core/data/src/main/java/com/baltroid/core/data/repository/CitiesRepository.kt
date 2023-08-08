package com.baltroid.core.data.repository

import com.baltroid.model.City
import com.baltroid.model.Mall
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun getCities(): Flow<List<City>>
    fun getMalls(): Flow<List<Mall>>
}