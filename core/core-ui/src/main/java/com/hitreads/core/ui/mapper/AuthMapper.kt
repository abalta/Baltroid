package com.hitreads.core.ui.mapper

import com.hitreads.core.domain.model.LoginModel
import com.hitreads.core.model.Login

fun LoginModel.asLogin() = Login(
    avatar, token, userId, username, wallet
)