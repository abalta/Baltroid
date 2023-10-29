package com.baltroid.core.data.model

import com.baltroid.core.firestore.model.NetworkCategory
import com.baltroid.model.Category

fun NetworkCategory.asCategory() = Category(
    code = code ?: 0,
    name = name.orEmpty()
)