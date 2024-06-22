package com.mobven.domain.model

data class CourseModel(
    val id: Int,
    val title: String,
    val description: String,
    val popular: Boolean,
    val author: String,
    val authorId: Int,
    val cover: String,
    val academy: String,
    val level: String,
    val commentCount: Int,
    val ratingAvg: Double,
    val ratings: List<Int>,
    val chapters: List<ChapterModel>,
    val comments: List<CommentModel>,
    val isFavorite: Boolean,
    val isSale: Boolean,
    val page: String,
    val duration: String
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

data class CommentModel(
    val id: Int,
    val comment: String,
    val rating: Int,
    val user: UserModel
)

data class UserModel(
    val id: Int,
    val name: String,
    val avatar: String
)

data class CategoryModel(
    val id: Int,
    val name: String
)
