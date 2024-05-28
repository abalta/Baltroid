package com.baltroid.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.baltroid.core.common.BaltroidResult
import com.baltroid.core.common.HttpException
import com.baltroid.core.common.PreferencesHelper
import com.baltroid.core.common.isFailure
import com.baltroid.core.common.isSuccess
import com.baltroid.core.data.mapper.asAcademyDetailModel
import com.baltroid.core.data.mapper.asAcademyModel
import com.baltroid.core.data.mapper.asCourseDetailModel
import com.baltroid.core.data.mapper.asCourseResponseModel
import com.baltroid.core.data.mapper.asLoginResponseModel
import com.baltroid.core.data.mapper.asProfileModel
import com.baltroid.core.data.mapper.asTeacherDetailModel
import com.baltroid.core.data.mapper.asTeacherModel
import com.baltroid.core.data.mapper.pagingMap
import com.baltroid.core.network.source.AcademyPagingDataSource
import com.baltroid.core.network.source.CoursePagingDataSource
import com.baltroid.core.network.source.MekikNetworkDataSource
import com.baltroid.core.network.source.TeacherPagingDataSource
import com.mobven.domain.model.AcademyDetailModel
import com.mobven.domain.model.AcademyModel
import com.mobven.domain.model.CourseDetailModel
import com.mobven.domain.model.CourseModel
import com.mobven.domain.model.LoginResponseModel
import com.mobven.domain.model.ProfileModel
import com.mobven.domain.model.TeacherDetailModel
import com.mobven.domain.model.TeacherModel
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
    override fun login(
        email: String,
        password: String
    ): Flow<BaltroidResult<LoginResponseModel>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.login(email, password)
        if (response.isSuccess()) {
            emit(BaltroidResult.success(response.value.result!!.asLoginResponseModel().also {
                saveToken(it.token)
            }))
        } else if (response.isFailure()) {
            if (response.error is HttpException) {
                emit(BaltroidResult.failure(response.error))
            } else {
                emit(BaltroidResult.failure(Throwable(COMMON_ERROR_MESSAGE)))
            }

        }
    }.flowOn(Dispatchers.IO)

    override fun register(
        email: String,
        password: String,
        firstname: String,
        lastname: String,
        agreement: Boolean
    ): Flow<BaltroidResult<LoginResponseModel>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.register(
            email = email,
            password = password,
            fisrtname = firstname,
            lastname = lastname,
            agreement = agreement
        )
        if (response.isSuccess()) {
            emit(BaltroidResult.success(response.value.result!!.asLoginResponseModel().also {
                saveToken(it.token)
            }))
        } else if (response.isFailure()) {
            if (response.error is HttpException) {
                emit(BaltroidResult.failure(response.error))
            } else {
                emit(BaltroidResult.failure(Throwable(COMMON_ERROR_MESSAGE)))
            }

        }
    }.flowOn(Dispatchers.IO)

    override fun saveToken(token: String) {
        preferencesHelper.userAccessToken = token
    }

    override fun isUserLoggedIn(): Boolean = preferencesHelper.userAccessToken.isNullOrEmpty().not()
    override fun getCourses(): Flow<PagingData<CourseModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                CoursePagingDataSource(networkDataSource)
            }
        ).flow.pagingMap {
            it.asCourseResponseModel()
        }
    }

    override fun getTeachers(): Flow<PagingData<TeacherModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                TeacherPagingDataSource(networkDataSource)
            }
        ).flow.pagingMap {
            it.asTeacherModel()
        }
    }

    override fun getAcademies(): Flow<PagingData<AcademyModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                AcademyPagingDataSource(networkDataSource)
            }
        ).flow.pagingMap {
            it.asAcademyModel()
        }
    }

    override fun getLimitedCourses(
        limit: Int,
        sort: String?
    ): Flow<BaltroidResult<List<CourseModel>>> =
        flow {
            emit(BaltroidResult.loading())
            val response = networkDataSource.courses(limit = limit, sort = sort)
            if (response.isSuccess()) {
                emit(BaltroidResult.success(response.value.result!!.courses.map {
                    it.asCourseResponseModel()
                }))
            } else if (response.isFailure()) {
                if (response.error is HttpException) {
                    emit(BaltroidResult.failure(response.error))
                } else {
                    emit(BaltroidResult.failure(Throwable(COMMON_ERROR_MESSAGE)))
                }
            }
        }.flowOn(Dispatchers.IO)

    override fun getCourseDetail(id: Int): Flow<BaltroidResult<CourseDetailModel>> =
        flow {
            emit(BaltroidResult.loading())
            val response = networkDataSource.courseDetail(id)
            if (response.isSuccess()) {
                emit(BaltroidResult.success(response.value.result!!.asCourseDetailModel()))
            } else if (response.isFailure()) {
                if (response.error is HttpException) {
                    emit(BaltroidResult.failure(response.error))
                } else {
                    emit(BaltroidResult.failure(Throwable(COMMON_ERROR_MESSAGE)))
                }
            }
        }.flowOn(Dispatchers.IO)

    override fun getTeacherDetail(id: Int): Flow<BaltroidResult<TeacherDetailModel>> =
        flow {
            emit(BaltroidResult.loading())
            val response = networkDataSource.teacherDetail(id)
            if (response.isSuccess()) {
                emit(BaltroidResult.success(response.value.result!!.asTeacherDetailModel()))
            } else if (response.isFailure()) {
                if (response.error is HttpException) {
                    emit(BaltroidResult.failure(response.error))
                } else {
                    emit(BaltroidResult.failure(Throwable(COMMON_ERROR_MESSAGE)))
                }
            }
        }.flowOn(Dispatchers.IO)

    override fun getAcademyDetail(id: Int): Flow<BaltroidResult<AcademyDetailModel>> =
        flow {
            emit(BaltroidResult.loading())
            val response = networkDataSource.academyDetail(id)
            if (response.isSuccess()) {
                emit(BaltroidResult.success(response.value.result!!.asAcademyDetailModel()))
            } else if (response.isFailure()) {
                if (response.error is HttpException) {
                    emit(BaltroidResult.failure(response.error))
                } else {
                    emit(BaltroidResult.failure(Throwable(COMMON_ERROR_MESSAGE)))
                }
            }
        }.flowOn(Dispatchers.IO)

    override fun getProfile(): Flow<BaltroidResult<ProfileModel>> =
        flow {
            emit(BaltroidResult.loading())
            val response = networkDataSource.profile()
            if (response.isSuccess()) {
                emit(BaltroidResult.success(response.value.result!!.asProfileModel()))
            } else if (response.isFailure()) {
                if (response.error is HttpException) {
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