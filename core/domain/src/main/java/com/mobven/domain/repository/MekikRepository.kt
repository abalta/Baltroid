package com.mobven.domain.repository

import androidx.paging.PagingData
import com.baltroid.core.common.BaltroidResult
import com.baltroid.core.network.model.AcademyEntityDto
import com.mobven.domain.model.AcademyDetailModel
import com.mobven.domain.model.AcademyModel
import com.mobven.domain.model.CourseModel
import com.mobven.domain.model.LoginResponseModel
import com.mobven.domain.model.ProfileModel
import com.mobven.domain.model.SearchModel
import com.mobven.domain.model.TeacherDetailModel
import com.mobven.domain.model.TeacherModel
import com.mobven.domain.model.TotalModel
import com.mobven.domain.model.VideoModel
import kotlinx.coroutines.flow.Flow

interface MekikRepository {
    fun login(email: String, password: String): Flow<BaltroidResult<LoginResponseModel>>
    fun register(email: String, password: String, firstname: String, lastname: String, agreement: Boolean): Flow<BaltroidResult<LoginResponseModel>>
    fun saveToken(token: String)
    fun deleteToken()
    fun isUserLoggedIn(): Boolean
    fun getCourses(): Flow<PagingData<CourseModel>>
    fun getTeachers(): Flow<PagingData<TeacherModel>>
    fun getAcademies(): Flow<PagingData<AcademyModel>>
    fun getLimitedCourses(limit: Int, sort: String?): Flow<BaltroidResult<List<CourseModel>>>
    fun getCourseDetail(id: Int): Flow<BaltroidResult<CourseModel>>
    fun getTeacherDetail(id: Int): Flow<BaltroidResult<TeacherDetailModel>>
    fun getAcademyDetail(id: Int): Flow<BaltroidResult<AcademyDetailModel>>
    fun getProfile(): Flow<BaltroidResult<ProfileModel>>
    fun updateProfile(email: String?, firstname: String?, lastname: String?, phone: String?, about: String?): Flow<BaltroidResult<ProfileModel>>
    fun addComment(courseId: Int, comment: String, rating: Int): Flow<BaltroidResult<Boolean>>
    fun search(query: String): Flow<BaltroidResult<SearchModel>>
    fun allTotal(): Flow<BaltroidResult<TotalModel>>
    fun video(id: String): Flow<BaltroidResult<VideoModel>>
    fun getFavorites(): Flow<BaltroidResult<List<CourseModel>>>
    fun addFavorite(id: Int): Flow<BaltroidResult<Boolean>>
    fun deleteFavorite(id: Int): Flow<BaltroidResult<Boolean>>


}