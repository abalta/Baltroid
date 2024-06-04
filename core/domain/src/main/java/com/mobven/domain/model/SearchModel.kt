package com.mobven.domain.model

data class SearchModel(
    val courses: List<CourseModel>,
    val academies: List<AcademyModel>,
    val teachers: List<TeacherModel>
)

data class TotalModel(
    val total: String
)