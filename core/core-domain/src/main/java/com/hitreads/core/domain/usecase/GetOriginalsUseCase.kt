package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.OriginalRepository
import javax.inject.Inject

class GetOriginalsUseCase @Inject constructor(private val repository: OriginalRepository) {
    operator fun invoke(getByFav: Boolean? = null) =
        repository.getOriginals(getByFav)
}
