package com.baltroid.core.data.model

import com.baltroid.core.firestore.model.NetworkMall
import com.baltroid.model.Mall

fun NetworkMall.asMall() = Mall(
    id = id.orEmpty(),
    cityCode = cityCode ?: 0,
    address = address.orEmpty(),
    email = email.orEmpty(),
    floors = floors.orEmpty(),
    location = Pair(location?.latitude ?: 0.0, location?.longitude ?: 0.0),
    name = name.orEmpty(),
    phone = phone.orEmpty(),
    services = services.orEmpty(),
    web = web.orEmpty(),
    logo = logo.orEmpty(),
    photos = photos.orEmpty()
)