package com.hitreads.core.ui.mapper

import com.hitreads.core.domain.model.LoginModel
import com.hitreads.core.domain.model.ProfileModel
import com.hitreads.core.model.Login
import com.hitreads.core.model.Profile

fun LoginModel.asLogin() = Login(
    avatar, token, userId, username, wallet
)

fun ProfileModel.asProfile() = Profile(
    name = name,
    userName = userName,
    email = email,
    karma = karma,
    avatar = avatar,
    is_beta = is_beta,
    gem = gem,
    imgUrl = ""
)
