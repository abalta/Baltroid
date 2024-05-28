package com.mobven.domain.model

data class AcademyModel(
    val id: Int,
    val name: String,
    val courseCount: String,
    val teacherCount: String,
    val logo: String
)

data class AcademyDetailModel(
    val id: Int,
    val name: String,
    val logo: String,
    val courses: List<CourseModel>,
    val teachers: List<TeacherModel>,
    val coursesCount: Int,
    val about: String
)