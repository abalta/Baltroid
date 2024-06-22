package com.mobven.domain.usecase

import com.mobven.domain.repository.MekikRepository
import javax.inject.Inject

class CourseUseCase @Inject constructor(
    private val mekikRepository: MekikRepository
){
    operator fun invoke(sort: String? = null, category: Int? = null) = mekikRepository.getCourses(sort, category)
    operator fun invoke(limit: Int, sort: String? = null) = mekikRepository.getLimitedCourses(limit, sort)
}