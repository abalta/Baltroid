package com.mobven.domain.usecase

import com.mobven.domain.repository.MekikRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val mekikRepository: MekikRepository
){
    operator fun invoke() = mekikRepository.getProfile()

    operator fun invoke(
        email: String?,
        firstname: String?,
        lastname: String?,
        phone: String?,
        about: String?
    ) = mekikRepository.updateProfile(email, firstname, lastname, phone, about)

    fun delete() = mekikRepository.deleteProfile()
}