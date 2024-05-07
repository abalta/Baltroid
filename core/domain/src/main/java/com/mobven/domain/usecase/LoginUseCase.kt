package com.mobven.domain.usecase

import com.mobven.domain.repository.MekikRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val mekikRepository: MekikRepository
){
    operator fun invoke(email: String, password: String) = mekikRepository.login(email, password)
}