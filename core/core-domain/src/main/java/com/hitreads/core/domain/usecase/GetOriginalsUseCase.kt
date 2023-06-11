package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.OriginalRepository
import javax.inject.Inject

class GetOriginalsUseCase @Inject constructor(private val repository: OriginalRepository) {
    operator fun invoke(filter: String? = null) = repository.getOriginals(filter)
}
