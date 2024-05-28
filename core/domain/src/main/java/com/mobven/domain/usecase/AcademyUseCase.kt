package com.mobven.domain.usecase

import com.mobven.domain.repository.MekikRepository
import javax.inject.Inject

class AcademyUseCase @Inject constructor(
    private val mekikRepository: MekikRepository
){
    operator fun invoke() = mekikRepository.getAcademies()
}