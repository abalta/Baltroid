package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.ChapterDto
import com.baltroid.core.network.model.CourseDetailDto
import com.baltroid.core.network.model.CourseDto
import com.baltroid.core.network.model.LessonDto
import com.mobven.domain.model.ChapterModel
import com.mobven.domain.model.CourseDetailModel
import com.mobven.domain.model.CourseModel
import com.mobven.domain.model.LessonModel

fun CourseDto.asCourseResponseModel() = CourseModel(
    id = id ?: 0,
    title = title.orEmpty(),
    description = description.orEmpty(),
    popular = isPromoted ?: false,
    author = author?.name.orEmpty(),
    cover = cover.orEmpty()
)

fun CourseDetailDto.asCourseDetailModel() = CourseDetailModel(
    id = course?.id ?: 0,
    title = course?.title.orEmpty(),
    description = course?.description?.replace("&nbsp;", " ").orEmpty(),
    author = course?.author?.name.orEmpty(),
    academy = course?.academy?.academyName.orEmpty(),
    level = course?.level.orEmpty(),
    cover = course?.cover.orEmpty(),
    commentCount = course?.commentCount ?: 0,
    ratingAvg = course?.ratingAvg ?: 0.0,
    chapters = course?.chapters?.map { it.asChapterModel() } ?: emptyList()
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