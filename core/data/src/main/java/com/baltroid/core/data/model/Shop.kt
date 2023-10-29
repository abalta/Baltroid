package com.baltroid.core.data.model

import com.baltroid.core.firestore.model.NetworkShop
import com.baltroid.model.Shop

fun NetworkShop.asShop() = Shop(
    code = code ?: 0,
    name = name.orEmpty(),
    categoryCode = categoryCode ?: 0
)