package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.ChapterDto
import com.baltroid.core.network.model.CommentDto
import com.baltroid.core.network.model.CourseDto
import com.baltroid.core.network.model.LessonDto
import com.baltroid.core.network.model.UserDto
import com.mobven.domain.model.ChapterModel
import com.mobven.domain.model.CommentModel
import com.mobven.domain.model.CourseModel
import com.mobven.domain.model.LessonModel
import com.mobven.domain.model.UserModel

fun CourseDto.asCourseResponseModel() = CourseModel(
    id = id ?: 0,
    title = title.orEmpty(),
    description = description.orEmpty(),
    popular = isPromoted ?: false,
    author = author?.name.orEmpty(),
    cover = cover.orEmpty(),
    academy = academy?.academyName.orEmpty(),
    level = level.orEmpty(),
    commentCount = commentCount ?: 0,
    ratingAvg = ratingAvg ?: 0.0,
    chapters = chapters?.map { it.asChapterModel() } ?: emptyList(),
    comments = comments?.map { it.asCommentModel() } ?: emptyList(),
    ratings = comments?.map { it.rating ?: 0 } ?: emptyList()
)

fun ChapterDto.asChapterModel() = ChapterModel(
    id = id ?: 0,
    name = chapterName.orEmpty(),
    lessons = lessons?.map { it.asLessonModel() } ?: emptyList()
)

fun LessonDto.asLessonModel() = LessonModel(
    id = id ?: 0,
    name = lessonName.orEmpty(),
    length = length.orEmpty(),
    playerId = playerId.orEmpty(),
    isPromo = isPromo ?: false
)

fun CommentDto.asCommentModel() = CommentModel(
    id = id ?: 0,
    comment = comment.orEmpty(),
    rating = rating ?: 0,
    user = user?.asUserModel() ?: UserModel(0, "", "")
)

fun UserDto.asUserModel() = UserModel(
    id = id ?: 0,
    name = "$firstname $lastname",
    avatar = avatar.orEmpty()
)