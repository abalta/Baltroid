package com.mobven.domain.usecase

import com.mobven.domain.repository.MekikRepository
import javax.inject.Inject

class CourseUseCase @Inject constructor(
    private val mekikRepository: MekikRepository
){
    operator fun invoke() = mekikRepository.getCourses()
    operator fun invoke(limit: Int, sort: String? = null) = mekikRepository.getLimitedCourses(limit, sort)
}