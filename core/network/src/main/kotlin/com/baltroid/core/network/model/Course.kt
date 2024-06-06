package com.baltroid.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseListDto(
    @SerialName("courses")
    val courses: List<CourseDto>,
    @SerialName("paginate")
    val paginate: PaginateDto
)

@Serializable
data class CourseDto(
    @SerialName("author")
    val author: AuthorDto?,
    @SerialName("category")
    val category: CategoryDto?,
    @SerialName("cover")
    val cover: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("currency")
    val currency: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("duration")
    val duration: String?,
    @SerialName("level")
    val level: String?,
    @SerialName("old_price")
    val oldPrice: String?,
    @SerialName("price")
    val price: String?,
    @SerialName("slug")
    val slug: String?,
    @SerialName("sort")
    val sort: Int?,
    @SerialName("title")
    val title: String?,
    @SerialName("total_lesson")
    val totalLesson: Int?,
    @SerialName("academy")
    val academy: AcademyDto?,
    @SerialName("cover_steep_image")
    val coverSteep: String?,
    @SerialName("is_promoted")
    val isPromoted: Boolean?,
    @SerialName("chapters")
    val chapters: List<ChapterDto>?,
    @SerialName("comment_count")
    val commentCount: Int?,
    @SerialName("rating_avg")
    val ratingAvg: Double?,
    @SerialName("comments")
    val comments: List<CommentDto>?,
    @SerialName("is_sale")
    val isSale: Int?,
    @SerialName("is_favorite")
    val isFavorite: Int?,
)

@Serializable
data class CommentDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("comment")
    val comment: String?,
    @SerialName("rating")
    val rating: Int?,
    @SerialName("user")
    val user: UserDto?
)

@Serializable
data class UserDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("firstname")
    val firstname: String?,
    @SerialName("lastname")
    val lastname: String?,
    @SerialName("avatar")
    val avatar: String?,
)

@Serializable
data class ChapterDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("course_id")
    val courseId: Int?,
    @SerialName("chapter_name")
    val chapterName: String?,
    @SerialName("slug")
    val slug: String?,
    @SerialName("lessons")
    val lessons: List<LessonDto>?
)

@Serializable
data class AcademyDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("academy_name")
    val academyName: String?,
    @SerialName("slug")
    val slug: String?,
    @SerialName("logo")
    val logo: String?,
    @SerialName("courses")
    val courses: Int?,
    @SerialName("teacher")
    val teachers: Int?,
    @SerialName("about")
    val about: String?,
)
@Serializable
data class LessonDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("is_active")
    val isActive: Boolean?,
    @SerialName("is_promo")
    val isPromo: Boolean?,
    @SerialName("length")
    val length: String?,
    @SerialName("lesson_name")
    val lessonName: String?,
    @SerialName("path")
    val path: String?,
    @SerialName("slug")
    val slug: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("chapter_id")
    val chapterId: Int?,
    @SerialName("player_id")
    val playerId: String?,
)

@Serializable
data class AuthorDto(
    @SerialName("avatar")
    val avatar: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("slug")
    val slug: String?
)

@Serializable
data class PaginateDto(
    @SerialName("current")
    val current: Int,
    @SerialName("data_count")
    val dataCount: Int?,
    @SerialName("total")
    val total: Int
)

@Serializable
data class CategoryDto(
    @SerialName("parent_category")
    val parentCategory: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("slug")
    val slug: String?
)
