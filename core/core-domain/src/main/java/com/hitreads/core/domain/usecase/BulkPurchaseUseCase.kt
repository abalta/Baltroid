package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.OriginalRepository
import javax.inject.Inject

class BulkPurchaseUseCase @Inject constructor(private val repository: OriginalRepository) {
    operator fun invoke(originalId: Int) = repository.bulkPurchase(originalId)
}
