package com.mobven.domain.usecase

import com.mobven.domain.repository.MekikRepository
import javax.inject.Inject

class TeacherDetailUseCase @Inject constructor(
    private val mekikRepository: MekikRepository
){
    operator fun invoke(id: Int) = mekikRepository.getTeacherDetail(id)
}