package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.OriginalRepository
import javax.inject.Inject

class GetOriginalsUseCase @Inject constructor(private val repository: OriginalRepository) {
    operator fun invoke(filter: String? = null, getByFav: Boolean? = null) =
        repository.getOriginals(filter, getByFav)
}
