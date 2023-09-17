package com.baltroid.domain

import com.baltroid.core.data.repository.CitiesRepository
import com.baltroid.model.Service
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetServicesUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    operator fun invoke(): Flow<List<Service>> = citiesRepository.getServices()
}