package com.mobven.domain.usecase

import com.mobven.domain.repository.MekikRepository
import javax.inject.Inject

class PlayerUseCase @Inject constructor(
    private val mekikRepository: MekikRepository
) {
    operator fun invoke(playerId: String) = mekikRepository.video(playerId)
}