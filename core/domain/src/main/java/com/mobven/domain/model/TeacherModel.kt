package com.mobven.domain.model

data class TeacherModel(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val photo: String,
    val academyName: String,
    val courses: String
)

data class TeacherDetailModel(
    val id: Int,
    val name: String,
    val photo: String,
    val biography: String,
    val academyName: String,
    val coursesCount: Int,
    val courses: List<CourseModel>
)