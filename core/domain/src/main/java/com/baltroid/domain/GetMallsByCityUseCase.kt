package com.baltroid.domain

import com.baltroid.core.data.repository.CitiesRepository
import com.baltroid.model.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetMallsByCityUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    operator fun invoke(cityCode: Int): Flow<City> {
        return combine(
            citiesRepository.getCities(),
            citiesRepository.getMalls()
        ) { cities, malls ->
            val selectedCity = cities.find {
                it.code == cityCode
            }
            malls.forEach { mall ->
                if(selectedCity?.malls?.contains(mall) == false && selectedCity.code == mall.cityCode) {
                    selectedCity.malls.add(mall)
                }
            }
            selectedCity!!
        }
    }
}