package com.baltroid.domain

import com.baltroid.core.data.repository.CitiesRepository
import com.baltroid.model.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetMallsWithCitiesUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    operator fun invoke(): Flow<List<City>> {
        return combine(
            citiesRepository.getCities(),
            citiesRepository.getMalls()
        ) { cities, malls ->
            malls.forEach { mall ->
                cities.firstOrNull { it.code == mall.cityCode }?.malls?.add(mall)
            }
            cities.sortedByDescending {
                it.malls.size
            }
        }
    }
}