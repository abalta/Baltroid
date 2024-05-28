package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.TeacherDto
import com.mobven.domain.model.TeacherDetailModel
import com.mobven.domain.model.TeacherModel

fun TeacherDto.asTeacherModel() = TeacherModel(
    id = id ?: 0,
    name = "$firstname $lastname",
    email = email.orEmpty(),
    phone = phone.orEmpty(),
    photo = photo.orEmpty(),
    academyName = academy?.academyName.orEmpty(),
    courses = "$coursesCount Eğitim"
)

fun TeacherDto.asTeacherDetailModel() = TeacherDetailModel(
    id = id ?: 0,
    name = "$firstname $lastname",
    photo = photo.orEmpty(),
    biography = biography.orEmpty(),
    academyName = academy?.academyName.orEmpty(),
    coursesCount = coursesCount ?: 0,
    courses = courses?.map { it.asCourseResponseModel() } ?: emptyList()
)