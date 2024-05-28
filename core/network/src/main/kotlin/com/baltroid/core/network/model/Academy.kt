package com.baltroid.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AcademyListDto(
    @SerialName("academy")
    val courses: List<AcademyDto>,
    @SerialName("paginate")
    val paginate: PaginateDto
)

@Serializable
data class AcademyDetailDto(
    @SerialName("academy")
    val academy: AcademyEntityDto
)

@Serializable
data class AcademyEntityDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("academy_name")
    val academyName: String?,
    @SerialName("slug")
    val slug: String?,
    @SerialName("logo")
    val logo: String?,
    @SerialName("teacher")
    val teachers: List<TeacherDto>?,
    @SerialName("about")
    val about: String?,
    @SerialName("courses")
    val courses: List<CourseDto>?
)