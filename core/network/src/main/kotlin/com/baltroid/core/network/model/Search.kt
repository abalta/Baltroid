package com.baltroid.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchDto(
    @SerialName("courses")
    val courses: List<CourseDto>,
    @SerialName("academy")
    val academies: List<AcademyDto>,
    @SerialName("teacher")
    val teachers: List<TeacherDto>
)

@Serializable
data class AllTotalDto(
    @SerialName("courses")
    val totalCourses: Int,
    @SerialName("academy")
    val totalAcademy: Int,
    @SerialName("teacher")
    val totalTeacher: Int
)