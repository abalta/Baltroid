package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.AuthRepository
import javax.inject.Inject

class IsLoggedUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke() = repository.isLogged()
}
