package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.AuthRepository
import com.hitreads.core.domain.repository.OriginalRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(email: String, password: String) = repository.login(email, password)
}
