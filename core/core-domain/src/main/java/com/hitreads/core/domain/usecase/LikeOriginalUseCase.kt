package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.OriginalRepository
import javax.inject.Inject

class LikeOriginalUseCase @Inject constructor(private val repository: OriginalRepository) {
    operator fun invoke(id: Int) = repository.likeOriginal(id)
}
