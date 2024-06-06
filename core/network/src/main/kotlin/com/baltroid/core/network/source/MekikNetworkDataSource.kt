package com.baltroid.core.network.source

import com.baltroid.core.common.BaltroidResult
import com.baltroid.core.network.api.service.MekikService
import com.baltroid.core.network.model.CourseListDto
import com.baltroid.core.network.model.DataResponse
import com.baltroid.core.network.model.LoginRequestDto
import com.baltroid.core.network.model.LoginResponseDto
import com.baltroid.core.network.model.ProfileDto
import com.baltroid.core.network.model.RegisterRequestDto
import javax.inject.Inject

class MekikNetworkDataSource @Inject constructor(private val mekikService: MekikService) {
    suspend fun login(
        email: String,
        password: String
    ): BaltroidResult<DataResponse<LoginResponseDto>> =
        mekikService.login(LoginRequestDto(email, password))

    suspend fun register(
        email: String,
        password: String,
        fisrtname: String,
        lastname: String,
        agreement: Boolean
    ): BaltroidResult<DataResponse<LoginResponseDto>> =
        mekikService.register(
            RegisterRequestDto(
                email = email,
                password = password,
                passwordConfirmation = password,
                firstName = fisrtname,
                lastName = lastname,
                agreement = agreement
            )
        )

    suspend fun courses(page: Int? = null, limit: Int? = null, sort: String? = null) =
        mekikService.getCourses(page, limit, sort)

    suspend fun courseDetail(id: Int) = mekikService.getCourseDetail(id)

    suspend fun teachers(page: Int? = null, limit: Int? = null, sort: String? = null) =
        mekikService.getTeachers(page, limit, sort)

    suspend fun teacherDetail(id: Int) = mekikService.getTeacherDetail(id)

    suspend fun academies(page: Int? = null, limit: Int? = null, sort: String? = null) =
        mekikService.getAcademies(page, limit, sort)

    suspend fun academyDetail(id: Int) = mekikService.getAcademyDetail(id)

    suspend fun profile() = mekikService.getProfile()

    suspend fun updateProfile(
        email: String? = null,
        firstname: String? = null,
        lastname: String? = null,
        phone: String? = null,
        about: String? = null
    ) = mekikService.updateProfile(
        ProfileDto(
            id = null,
            email = email,
            avatar = null,
            firstName = firstname,
            lastName = lastname,
            phone = phone,
            about = about
        )
    )

    suspend fun addComment(courseId: Int, comment: String, rating: Int) =
        mekikService.addComment(comment, courseId, rating)

    suspend fun search(query: String) =
        mekikService.search(query)

    suspend fun allTotal() = mekikService.allTotal()

    suspend fun video(id: String) = mekikService.video(id)

    suspend fun getFavorites() = mekikService.getFavorites()

    suspend fun addFavorite(courseId: Int) = mekikService.addFavorite(courseId)

    suspend fun removeFavorite(courseId: Int) = mekikService.removeFavorite(courseId)

}