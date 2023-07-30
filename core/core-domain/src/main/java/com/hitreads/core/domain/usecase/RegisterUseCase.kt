package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(
        name: String, email: String, password: String, userAgreement: Boolean,
        privacyPolicy: Boolean
    ) =
        repository.register(
            name,
            email,
            password,
            userAgreement = userAgreement,
            privacyPolicy = privacyPolicy
        )
}