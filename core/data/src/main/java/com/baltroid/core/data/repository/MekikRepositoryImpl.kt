package com.baltroid.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.baltroid.core.common.BaltroidResult
import com.baltroid.core.common.HttpException
import com.baltroid.core.common.PreferencesHelper
import com.baltroid.core.common.isFailure
import com.baltroid.core.common.isSuccess
import com.baltroid.core.data.extension.emptyToNull
import com.baltroid.core.data.mapper.asAcademyDetailModel
import com.baltroid.core.data.mapper.asAcademyModel
import com.baltroid.core.data.mapper.asCategoryModel
import com.baltroid.core.data.mapper.asCourseResponseModel
import com.baltroid.core.data.mapper.asLoginResponseModel
import com.baltroid.core.data.mapper.asProfileModel
import com.baltroid.core.data.mapper.asSearchModel
import com.baltroid.core.data.mapper.asTeacherDetailModel
import com.baltroid.core.data.mapper.asTeacherModel
import com.baltroid.core.data.mapper.asTotalModel
import com.baltroid.core.data.mapper.asVideoModel
import com.baltroid.core.data.mapper.pagingMap
import com.baltroid.core.network.model.AcademyEntityDto
import com.baltroid.core.network.model.DataResponse
import com.baltroid.core.network.source.AcademyPagingDataSource
import com.baltroid.core.network.source.CoursePagingDataSource
import com.baltroid.core.network.source.MekikNetworkDataSource
import com.baltroid.core.network.source.TeacherPagingDataSource
import com.mobven.domain.model.AcademyDetailModel
import com.mobven.domain.model.AcademyModel
import com.mobven.domain.model.CategoryModel
import com.mobven.domain.model.CourseModel
import com.mobven.domain.model.LoginResponseModel
import com.mobven.domain.model.ProfileModel
import com.mobven.domain.model.SearchModel
import com.mobven.domain.model.TeacherDetailModel
import com.mobven.domain.model.TeacherModel
import com.mobven.domain.model.TotalModel
import com.mobven.domain.model.VideoModel
import com.mobven.domain.repository.MekikRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MekikRepositoryImpl @Inject constructor(
    private val networkDataSource: MekikNetworkDataSource,
    private val preferencesHelper: PreferencesHelper,
) : MekikRepository {

    private val categoryList = mutableListOf<CategoryModel>()

    override fun login(
        email: String,
        password: String
    ): Flow<BaltroidResult<LoginResponseModel>> = networkRequestFlow(
        request = { networkDataSource.login(email, password) },
        onSuccess = { response ->
            response.asLoginResponseModel().also { saveToken(it.token) }
        }
    )

    override fun register(
        email: String,
        password: String,
        firstname: String,
        lastname: String,
        agreement: Boolean
    ): Flow<BaltroidResult<LoginResponseModel>> = networkRequestFlow(
        request = { networkDataSource.register(email, password, firstname, lastname, agreement) },
        onSuccess = { response ->
            response.asLoginResponseModel().also { saveToken(it.token) }
        }
    )

    override fun forgotPassword(email: String): Flow<BaltroidResult<Boolean>> = networkRequestFlow(
        request = { networkDataSource.forgotPassword(email) },
        onSuccess = { true }
    )

    override fun saveToken(token: String) {
        preferencesHelper.userAccessToken = token
    }

    override fun deleteToken() {
        preferencesHelper.removeUserAccessToken()
    }

    override fun isUserLoggedIn(): Boolean = preferencesHelper.userAccessToken.isNullOrEmpty().not()

    override fun getCourses(sort: String?, category: Int?): Flow<PagingData<CourseModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { CoursePagingDataSource(networkDataSource, sort, category) }
        ).flow.pagingMap { it.asCourseResponseModel() }
    }

    override fun getTeachers(): Flow<PagingData<TeacherModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { TeacherPagingDataSource(networkDataSource) }
        ).flow.pagingMap { it.asTeacherModel() }
    }

    override fun getAcademies(): Flow<PagingData<AcademyModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { AcademyPagingDataSource(networkDataSource) }
        ).flow.pagingMap { it.asAcademyModel() }
    }

    override fun getLimitedCourses(
        limit: Int,
        sort: String?
    ): Flow<BaltroidResult<List<CourseModel>>> =
        networkRequestFlow(
            request = { networkDataSource.courses(limit = limit, sort = sort) },
            onSuccess = { response -> response.courses.map { it.asCourseResponseModel() } }
        )

    override fun getCourseDetail(id: Int): Flow<BaltroidResult<CourseModel>> =
        networkRequestFlow(
            request = { networkDataSource.courseDetail(id) },
            onSuccess = { it.asCourseResponseModel() }
        )

    override fun getTeacherDetail(id: Int): Flow<BaltroidResult<TeacherDetailModel>> =
        networkRequestFlow(
            request = { networkDataSource.teacherDetail(id) },
            onSuccess = { it.asTeacherDetailModel() }
        )

    override fun getAcademyDetail(id: Int): Flow<BaltroidResult<AcademyDetailModel>> =
        networkRequestFlow(
            request = { networkDataSource.academyDetail(id) },
            onSuccess = { it.asAcademyDetailModel() }
        )

    override fun getProfile(): Flow<BaltroidResult<ProfileModel>> =
        networkRequestFlow(
            request = { networkDataSource.profile() },
            onSuccess = { it.asProfileModel() }
        )

    override fun updateProfile(
        email: String?,
        firstname: String?,
        lastname: String?,
        phone: String?,
        about: String?
    ): Flow<BaltroidResult<ProfileModel>> =
        networkRequestFlow(
            request = {
                networkDataSource.updateProfile(
                    email?.emptyToNull(),
                    firstname?.emptyToNull(),
                    lastname?.emptyToNull(),
                    phone?.emptyToNull(),
                    about?.emptyToNull()
                )
            },
            onSuccess = { it.asProfileModel() }
        )

    override fun addComment(
        courseId: Int,
        comment: String,
        rating: Int
    ): Flow<BaltroidResult<Boolean>> =
        networkRequestFlow(
            request = { networkDataSource.addComment(courseId, comment, rating) },
            onSuccess = { it }
        )

    override fun search(query: String): Flow<BaltroidResult<SearchModel>> =
        networkRequestFlow(
            request = { networkDataSource.search(query) },
            onSuccess = { it.asSearchModel() }
        )

    override fun allTotal(): Flow<BaltroidResult<TotalModel>> =
        networkRequestFlow(
            request = { networkDataSource.allTotal() },
            onSuccess = { it.asTotalModel() }
        )

    override fun video(id: String): Flow<BaltroidResult<VideoModel>> = networkRequestFlow(
        request = { networkDataSource.video(id) },
        onSuccess = { it.asVideoModel() }
    )

    override fun getFavorites(): Flow<BaltroidResult<List<CourseModel>>> = networkRequestFlow(
        request = { networkDataSource.getFavorites() },
        onSuccess = { response -> response.map { it.asCourseResponseModel() } }
    )

    override fun addFavorite(id: Int): Flow<BaltroidResult<Boolean>> =
        networkRequestFlow(
            request = { networkDataSource.addFavorite(id) },
            onSuccess = { true }
        )

    override fun deleteFavorite(id: Int): Flow<BaltroidResult<Boolean>> =
        networkRequestFlow(
            request = { networkDataSource.removeFavorite(id) },
            onSuccess = { true }
        )

    override fun getMyCourses(): Flow<BaltroidResult<List<CourseModel>>> =
        networkRequestFlow(
            request = { networkDataSource.userCourses() },
            onSuccess = { response -> response.map { it.asCourseResponseModel() } }
        )

    override fun getCategories(): Flow<BaltroidResult<List<CategoryModel>>> =
        networkRequestFlow(
            request = {
                if (categoryList.isEmpty()) {
                    networkDataSource.categories()
                } else {
                    BaltroidResult.success(DataResponse(mutableListOf(), null, null))
                }
            },
            onSuccess = { response ->
                val categories = response.map {
                    it.asCategoryModel()
                }
                categoryList.addAll(categories)
                categoryList
            }
        )

    private inline fun <T, R> networkRequestFlow(
        crossinline request: suspend () -> BaltroidResult<DataResponse<T>>,
        crossinline onSuccess: (T) -> R,
    ): Flow<BaltroidResult<R>> = flow {
        emit(BaltroidResult.loading())
        val response = request()
        if (response.isSuccess()) {
            emit(BaltroidResult.success(onSuccess(response.value.result!!)))
        } else if (response.isFailure()) {
            if (response.error is HttpException) {
                if ((response.error as HttpException).statusCode == 401) {
                    deleteToken()
                }
                emit(BaltroidResult.failure(response.error))
            } else {
                emit(BaltroidResult.failure(Throwable(COMMON_ERROR_MESSAGE)))
            }
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        private const val COMMON_ERROR_MESSAGE = "Bir Hata Oluştu."
    }
}