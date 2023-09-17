package com.baltroid.domain

import com.baltroid.core.data.repository.CitiesRepository
import com.baltroid.model.Mall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetMallUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    operator fun invoke(param: String): Flow<Mall> {
        return combine(
            citiesRepository.getServices(),
            citiesRepository.getMall(param)
        ) { services, mall ->
            mall.services.keys.forEach { key ->
                services.forEach { service ->
                    if (key == service.code) {
                        mall.services[key] = service
                    }
                }
            }
            mall
        }
    }
}