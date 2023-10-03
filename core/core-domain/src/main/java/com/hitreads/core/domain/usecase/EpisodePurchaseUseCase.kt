package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.OriginalRepository
import javax.inject.Inject

class EpisodePurchaseUseCase @Inject constructor(private val repository: OriginalRepository) {
    operator fun invoke(episodeId: Int) = repository.episodePurchase(episodeId)
}
