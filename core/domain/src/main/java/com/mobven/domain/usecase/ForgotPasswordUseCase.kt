package com.mobven.domain.usecase

import com.mobven.domain.repository.MekikRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val mekikRepository: MekikRepository
) {
    operator fun invoke(
        email: String
    ) = mekikRepository.forgotPassword(
        email = email
    )
}