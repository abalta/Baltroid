package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.AuthRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.logOut()
}
