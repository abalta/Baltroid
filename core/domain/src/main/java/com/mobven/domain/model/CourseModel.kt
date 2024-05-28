package com.mobven.domain.model

data class CourseModel(
    val id: Int,
    val title: String,
    val description: String,
    val popular: Boolean,
    val author: String,
    val cover: String
)

data class CourseDetailModel(
    val id: Int,
    val title: String,
    val description: String,
    val author: String,
    val academy: String,
    val level: String,
    val cover: String,
    val commentCount: Int,
    val chapters: List<ChapterModel>,
    val ratingAvg: Double
)
data class ChapterModel(
    val id: Int,
    val name: String,
    val lessons: List<LessonModel>
)

data class LessonModel(
    val id: Int,
    val name: String,
    val length: String,
    val playerId: String,
    val isPromo: Boolean
)
