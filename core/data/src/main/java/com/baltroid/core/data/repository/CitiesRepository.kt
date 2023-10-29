package com.baltroid.core.data.repository

import com.baltroid.model.City
import com.baltroid.model.Mall
import com.baltroid.model.Service
import com.baltroid.model.Shop
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun getCities(): Flow<List<City>>
    fun getMalls(): Flow<List<Mall>>
    fun getMall(id: String): Flow<Mall>
    fun getServices(): Flow<List<Service>>
    fun getShops(): Flow<List<Shop>>
}