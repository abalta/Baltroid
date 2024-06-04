package com.mobven.domain.usecase

import com.mobven.domain.repository.MekikRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val mekikRepository: MekikRepository
) {
    operator fun invoke(
        query: String
    ) = mekikRepository.search(
        query = query
    )

    operator fun invoke() = mekikRepository.allTotal()
}