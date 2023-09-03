package com.baltroid.domain

import com.baltroid.core.data.repository.CitiesRepository
import com.baltroid.model.City
import com.baltroid.model.Mall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetMallUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    operator fun invoke(param: String): Flow<Mall> = citiesRepository.getMall(param)
}