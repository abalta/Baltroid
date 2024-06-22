package com.baltroid.core.network.api.service

import com.baltroid.core.common.BaltroidResult
import com.baltroid.core.network.model.AcademyEntityDto
import com.baltroid.core.network.model.AcademyListDto
import com.baltroid.core.network.model.AllTotalDto
import com.baltroid.core.network.model.CategoryDto
import com.baltroid.core.network.model.CategoryMainDto
import com.baltroid.core.network.model.CategoryNameDto
import com.baltroid.core.network.model.CourseDto
import com.baltroid.core.network.model.CourseListDto
import com.baltroid.core.network.model.DataResponse
import com.baltroid.core.network.model.ForgotPasswordRequestDto
import com.baltroid.core.network.model.LoginRequestDto
import com.baltroid.core.network.model.LoginResponseDto
import com.baltroid.core.network.model.ProfileDto
import com.baltroid.core.network.model.RegisterRequestDto
import com.baltroid.core.network.model.SearchDto
import com.baltroid.core.network.model.TeacherDto
import com.baltroid.core.network.model.TeacherListDto
import com.baltroid.core.network.model.VideoDto
import com.baltroid.core.network.util.Constants
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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

    @POST(Constants.Path.FORGOT_PASSWORD)
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequestDto
    ): BaltroidResult<DataResponse<List<Unit>>>

    @GET(Constants.Path.COURSES)
    suspend fun getCourses(
        @Query(Constants.Fields.PAGE) page: Int? = null,
        @Query(Constants.Fields.LIMIT) limit: Int? = null,
        @Query(Constants.Fields.SORT) sort: String? = null,
        @Query(Constants.Fields.CATEGORY_ID) categoryId: Int? = null
    ): BaltroidResult<DataResponse<CourseListDto>>

    @GET("${Constants.Path.COURSES}/{${Constants.Fields.ID}}")
    suspend fun getCourseDetail(
        @Path(Constants.Fields.ID) id: Int
    ): BaltroidResult<DataResponse<CourseDto>>

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
    ): BaltroidResult<DataResponse<AcademyEntityDto>>

    @GET(Constants.Path.PROFILE)
    suspend fun getProfile(): BaltroidResult<DataResponse<ProfileDto>>

    @POST(Constants.Path.PROFILE)
    suspend fun updateProfile(
        @Body profile: ProfileDto
    ): BaltroidResult<DataResponse<ProfileDto>>

    @POST(Constants.Path.COMMENT)
    @FormUrlEncoded
    suspend fun addComment(
        @Field(Constants.Fields.COMMENT) comment: String,
        @Field(Constants.Fields.COURSE_ID) courseId: Int,
        @Field(Constants.Fields.RATING) rating: Int
    ): BaltroidResult<DataResponse<Boolean>>

    @GET(Constants.Path.SEARCH)
    suspend fun search(
        @Query(Constants.Fields.TITLE) query: String
    ): BaltroidResult<DataResponse<SearchDto>>

    @GET(Constants.Path.ALL_TOTAL)
    suspend fun allTotal(): BaltroidResult<DataResponse<AllTotalDto>>

    @POST(Constants.Path.VIDEO)
    suspend fun video(
        @Query(Constants.Fields.PLAYER_ID) playerId: String
    ): BaltroidResult<DataResponse<VideoDto>>

    @GET(Constants.Path.FAVORITE)
    suspend fun getFavorites(): BaltroidResult<DataResponse<List<CourseDto>>>

    @POST(Constants.Path.FAVORITE)
    suspend fun addFavorite(
        @Query(Constants.Fields.COURSE_ID) courseId: Int
    ): BaltroidResult<DataResponse<List<String>>>

    @DELETE(Constants.Path.FAVORITE)
    suspend fun removeFavorite(
        @Query(Constants.Fields.COURSE_ID) courseId: Int
    ): BaltroidResult<DataResponse<List<String>>>

    @GET(Constants.Path.USER_COURSES)
    suspend fun getUserCourses(): BaltroidResult<DataResponse<List<CourseDto>>>

    @GET(Constants.Path.CATEGORY)
    suspend fun getCategories(): BaltroidResult<DataResponse<List<CategoryMainDto>>>
}