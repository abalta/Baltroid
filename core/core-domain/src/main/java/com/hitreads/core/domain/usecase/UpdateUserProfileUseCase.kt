package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.AuthRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(
        avatarId: Int? = null,
        username: String? = null,
        nickname: String? = null,
        email: String? = null
    ) =
        repository.updateUserProfile(avatarId, username, nickname, email)
}