package com.baltroid.core.data.model

import com.baltroid.core.firestore.model.NetworkMall
import com.baltroid.model.Mall
import com.baltroid.model.Service

fun NetworkMall.asMall() = Mall(
    id = id.orEmpty(),
    cityCode = cityCode ?: 0,
    address = address.orEmpty(),
    email = email.orEmpty(),
    floors = floors.orEmpty(),
    location = Pair(location?.latitude ?: 0.0, location?.longitude ?: 0.0),
    name = name.orEmpty(),
    phone = phone.orEmpty(),
    services = services?.associate {
        it to Service(id = "", code = 0, name = "", icon = 0)
    }?.toMutableMap() ?: mutableMapOf(),
    web = web.orEmpty(),
    logo = logo.orEmpty(),
    photos = photos.orEmpty(),
    rating = rating.orEmpty(),
    reviews = reviews.orEmpty(),
    district = district.orEmpty()
)