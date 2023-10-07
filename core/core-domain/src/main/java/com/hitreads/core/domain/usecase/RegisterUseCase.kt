package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(
        name: String, username: String, email: String, password: String, userAgreement: Boolean,
        cookiePolicy: Boolean, birthdate: String, identifier: String
    ) =
        repository.register(
            name = name,
            username = username,
            email = email,
            password = password,
            userAgreement = userAgreement,
            cookiePolicy = cookiePolicy,
            identifier = identifier,
            birthdate = birthdate
        )
}