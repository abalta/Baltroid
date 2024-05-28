package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.AcademyDetailDto
import com.baltroid.core.network.model.AcademyDto
import com.mobven.domain.model.AcademyDetailModel
import com.mobven.domain.model.AcademyModel

fun AcademyDto.asAcademyModel() = AcademyModel(
    id = id ?: 0,
    name = academyName.orEmpty(),
    courseCount = "$courses Eğitim",
    teacherCount = "$teachers Eğitmen",
    logo = logo.orEmpty()
)

fun AcademyDetailDto.asAcademyDetailModel() = AcademyDetailModel(
    id = academy.id ?: 0,
    name = academy.academyName.orEmpty(),
    logo = academy.logo.orEmpty(),
    about = academy.about.orEmpty(),
    coursesCount = academy.courses?.size ?: 0,
    courses = academy.courses?.map { it.asCourseResponseModel() } ?: emptyList(),
    teachers = academy.teachers?.map { it.asTeacherModel() } ?: emptyList()
)