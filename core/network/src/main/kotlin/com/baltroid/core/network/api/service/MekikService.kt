package com.baltroid.core.network.api.service

import com.baltroid.core.common.BaltroidResult
import com.baltroid.core.network.model.AcademyDetailDto
import com.baltroid.core.network.model.AcademyListDto
import com.baltroid.core.network.model.CourseDetailDto
import com.baltroid.core.network.model.CourseListDto
import com.baltroid.core.network.model.DataResponse
import com.baltroid.core.network.model.LoginRequestDto
import com.baltroid.core.network.model.LoginResponseDto
import com.baltroid.core.network.model.ProfileEntity
import com.baltroid.core.network.model.RegisterRequestDto
import com.baltroid.core.network.model.TeacherDto
import com.baltroid.core.network.model.TeacherListDto
import com.baltroid.core.network.util.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MekikService {
    @POST(Constants.Path.LOGIN)
    suspend fun login(
        @Body request: LoginRequestDto
    ): BaltroidResult<DataResponse<LoginResponseDto>>

    @POST(Constants.Path.REGISTER)
    suspend fun register(
        @Body request: RegisterRequestDto
    ): BaltroidResult<DataResponse<LoginResponseDto>>

    @GET(Constants.Path.COURSES)
    suspend fun getCourses(
        @Query(Constants.Fields.PAGE) page: Int? = null,
        @Query(Constants.Fields.LIMIT) limit: Int? = null,
        @Query(Constants.Fields.SORT) sort: String? = null
    ): BaltroidResult<DataResponse<CourseListDto>>

    @GET("${Constants.Path.COURSES}/{${Constants.Fields.ID}}")
    suspend fun getCourseDetail(
        @Path(Constants.Fields.ID) id: Int
    ): BaltroidResult<DataResponse<CourseDetailDto>>

    @GET(Constants.Path.TEACHER)
    suspend fun getTeachers(
        @Query(Constants.Fields.PAGE) page: Int? = null,
        @Query(Constants.Fields.LIMIT) limit: Int? = null,
        @Query(Constants.Fields.SORT) sort: String? = null
    ): BaltroidResult<DataResponse<TeacherListDto>>

    @GET("${Constants.Path.TEACHER}/{${Constants.Fields.ID}}")
    suspend fun getTeacherDetail(
        @Path(Constants.Fields.ID) id: Int
    ): BaltroidResult<DataResponse<TeacherDto>>

    @GET(Constants.Path.ACADEMY)
    suspend fun getAcademies(
        @Query(Constants.Fields.PAGE) page: Int? = null,
        @Query(Constants.Fields.LIMIT) limit: Int? = null,
        @Query(Constants.Fields.SORT) sort: String? = null
    ): BaltroidResult<DataResponse<AcademyListDto>>

    @GET("${Constants.Path.ACADEMY}/{${Constants.Fields.ID}}")
    suspend fun getAcademyDetail(
        @Path(Constants.Fields.ID) id: Int
    ): BaltroidResult<DataResponse<AcademyDetailDto>>

    @GET(Constants.Path.PROFILE)
    suspend fun getProfile(): BaltroidResult<DataResponse<ProfileEntity>>

}