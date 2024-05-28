package com.mobven.domain.usecase

import com.mobven.domain.repository.MekikRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val mekikRepository: MekikRepository
) {
    operator fun invoke(
        email: String,
        password: String,
        firstname: String,
        lastname: String,
        agreement: Boolean
    ) = mekikRepository.register(
        email = email,
        password = password,
        firstname = firstname,
        lastname = lastname,
        agreement = agreement
    )
}