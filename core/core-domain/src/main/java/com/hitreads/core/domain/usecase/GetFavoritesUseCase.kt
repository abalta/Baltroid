package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: FavoriteRepository) {
    operator fun invoke(type: String, id: Int?) =
        repository.getFavorites(type, id)
}