package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoriteOriginalsUseCase @Inject constructor(private val repository: FavoriteRepository) {
    operator fun invoke() = repository.getFavoriteOriginals()
}