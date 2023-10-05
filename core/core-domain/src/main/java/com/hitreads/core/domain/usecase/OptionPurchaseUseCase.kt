package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.OriginalRepository
import javax.inject.Inject

class OptionPurchaseUseCase @Inject constructor(private val repository: OriginalRepository) {
    operator fun invoke(
        episodeId: Int, lineId: String, optionIndex: String, price: Int
    ) = repository.optionPurchase(episodeId, lineId, optionIndex, price)
}
