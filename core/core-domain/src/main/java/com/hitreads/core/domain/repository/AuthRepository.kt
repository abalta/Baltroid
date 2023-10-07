package com.hitreads.core.domain.repository

import com.baltroid.core.common.result.BaltroidResult
import com.hitreads.core.domain.model.AvatarModel
import com.hitreads.core.domain.model.LoginModel
import com.hitreads.core.domain.model.NotificationModel
import com.hitreads.core.domain.model.ProfileModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<BaltroidResult<LoginModel>>
    fun isLogged(): Flow<BaltroidResult<Boolean>>
    fun register(
        name: String,
        username: String,
        email: String,
        password: String,
        userAgreement: Boolean,
        cookiePolicy: Boolean,
        birthdate: String,
        identifier: String
    ): Flow<BaltroidResult<Unit?>>

    fun getProfile(): Flow<BaltroidResult<ProfileModel>>
    fun getAvatars(): Flow<BaltroidResult<List<AvatarModel>>>
    fun getAllNotifications(): Flow<BaltroidResult<List<NotificationModel>>>
    fun updateUserProfile(
        avatarId: Int?,
        username: String?,
        nickname: String?,
        email: String?
    ): Flow<BaltroidResult<Unit?>>

    suspend fun logOut()
    fun forgotPassword(email: String): Flow<BaltroidResult<Unit?>>
}