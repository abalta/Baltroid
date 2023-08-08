package com.baltroid.core.data.model

import com.baltroid.core.firestore.model.NetworkCity
import com.baltroid.model.City

fun NetworkCity.asCity() = City(
    code = code ?: 0,
    name = name.orEmpty(),
    malls = malls?.map {
        it.asMall()
    }.orEmpty()
)