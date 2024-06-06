package com.mobven.domain.usecase

import com.mobven.domain.repository.MekikRepository
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(
    private val mekikRepository: MekikRepository
) {
    operator fun invoke() = mekikRepository.getFavorites()

    fun invokeAdd(courseId: Int) = mekikRepository.addFavorite(courseId)

    fun invokeDelete(courseId: Int) = mekikRepository.deleteFavorite(courseId)
}