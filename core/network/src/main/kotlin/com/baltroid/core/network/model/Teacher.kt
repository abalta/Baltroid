package com.baltroid.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeacherListDto(
    @SerialName("teacher")
    val courses: List<TeacherDto>,
    @SerialName("paginate")
    val paginate: PaginateDto
)
@Serializable
data class TeacherDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("firstname")
    val firstname: String?,
    @SerialName("lastname")
    val lastname: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("phone")
    val phone: String?,
    @SerialName("avatar")
    val photo: String?,
    @SerialName("about_text")
    val biography: String?,
    @SerialName("academy")
    val academy: AcademyDto?,
    @SerialName("courses_count")
    val coursesCount: Int?,
    @SerialName("courses")
    val courses: List<CourseDto>?
)