package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.OriginalRepository
import javax.inject.Inject

class EndReadingEpisodeUseCase @Inject constructor(private val repository: OriginalRepository) {
    operator fun invoke(episodeId: Int) = repository.endReadingEpisode(episodeId)
}
