package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.model.OriginalType
import com.hitreads.core.domain.repository.OriginalRepository
import javax.inject.Inject

class ShowEpisodeUseCase @Inject constructor(private val repository: OriginalRepository) {
    operator fun invoke(id: Int, type: OriginalType) = repository.showEpisode(id, type)
}
