package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.AuthRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke() = repository.getProfile()
}