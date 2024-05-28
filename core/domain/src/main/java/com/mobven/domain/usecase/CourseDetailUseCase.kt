package com.mobven.domain.usecase

import com.mobven.domain.repository.MekikRepository
import javax.inject.Inject

class CourseDetailUseCase @Inject constructor(
    private val mekikRepository: MekikRepository
){
    operator fun invoke(id: Int) = mekikRepository.getCourseDetail(id)
}