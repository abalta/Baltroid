package com.baltroid.core.data.repository

import com.baltroid.model.City
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun getCities(): Flow<List<City>>
}