package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.AcademyDto
import com.baltroid.core.network.model.AcademyEntityDto
import com.mobven.domain.model.AcademyDetailModel
import com.mobven.domain.model.AcademyModel

fun AcademyDto.asAcademyModel() = AcademyModel(
    id = id ?: 0,
    name = academyName.orEmpty(),
    courseCount = "$courses Eğitim",
    teacherCount = "$teachers Eğitmen",
    logo = logo.orEmpty()
)

fun AcademyEntityDto.asAcademyDetailModel() = AcademyDetailModel(
    id = id ?: 0,
    name = academyName.orEmpty(),
    logo = logo.orEmpty(),
    about = about.orEmpty(),
    coursesCount = courses?.size ?: 0,
    courses = courses?.map { it.asCourseResponseModel() } ?: emptyList(),
    teachers = teachers?.map { it.asTeacherModel() } ?: emptyList()
)