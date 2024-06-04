package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.AllTotalDto
import com.baltroid.core.network.model.SearchDto
import com.mobven.domain.model.SearchModel
import com.mobven.domain.model.TotalModel

fun SearchDto.asSearchModel() = SearchModel(
    courses = courses.map { it.asCourseResponseModel() },
    academies = academies.map { it.asAcademyModel() },
    teachers = teachers.map { it.asTeacherModel() }
)

fun AllTotalDto.asTotalModel() = TotalModel(
    total = "$totalCourses eğitim, $totalTeacher eğitmen ve $totalAcademy akademi"
)