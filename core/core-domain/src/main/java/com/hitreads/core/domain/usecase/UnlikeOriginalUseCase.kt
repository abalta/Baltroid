package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.OriginalRepository
import javax.inject.Inject

class UnlikeOriginalUseCase @Inject constructor(private val repository: OriginalRepository) {
    operator fun invoke(id: Int) = repository.unlikeOriginal(id)
}
