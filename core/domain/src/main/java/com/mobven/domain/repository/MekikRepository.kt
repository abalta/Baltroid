package com.mobven.domain.repository

import androidx.paging.PagingData
import com.baltroid.core.common.BaltroidResult
import com.mobven.domain.model.AcademyDetailModel
import com.mobven.domain.model.AcademyModel
import com.mobven.domain.model.CourseDetailModel
import com.mobven.domain.model.CourseModel
import com.mobven.domain.model.LoginResponseModel
import com.mobven.domain.model.ProfileModel
import com.mobven.domain.model.TeacherDetailModel
import com.mobven.domain.model.TeacherModel
import kotlinx.coroutines.flow.Flow

interface MekikRepository {
    fun login(email: String, password: String): Flow<BaltroidResult<LoginResponseModel>>
    fun register(email: String, password: String, firstname: String, lastname: String, agreement: Boolean): Flow<BaltroidResult<LoginResponseModel>>
    fun saveToken(token: String)
    fun isUserLoggedIn(): Boolean
    fun getCourses(): Flow<PagingData<CourseModel>>
    fun getTeachers(): Flow<PagingData<TeacherModel>>
    fun getAcademies(): Flow<PagingData<AcademyModel>>
    fun getLimitedCourses(limit: Int, sort: String?): Flow<BaltroidResult<List<CourseModel>>>
    fun getCourseDetail(id: Int): Flow<BaltroidResult<CourseDetailModel>>
    fun getTeacherDetail(id: Int): Flow<BaltroidResult<TeacherDetailModel>>
    fun getAcademyDetail(id: Int): Flow<BaltroidResult<AcademyDetailModel>>
    fun getProfile(): Flow<BaltroidResult<ProfileModel>>

}