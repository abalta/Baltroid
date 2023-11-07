package com.baltroid.core.data.model

import com.baltroid.core.firestore.model.NetworkShop
import com.baltroid.core.firestore.model.NetworkShopDetail
import com.baltroid.model.Shop
import com.baltroid.model.ShopDetail

fun NetworkShop.asShop() = Shop(
    code = code ?: 0,
    name = name.orEmpty(),
    categoryCode = categoryCode ?: 0,
    logo = logo ?: "gs://mallquest.appspot.com/shop_logo/adidas.png",
    shopDetail = ShopDetail(
        code = networkShopDetail?.code ?: 0,
        floor = networkShopDetail?.floor ?: 0,
        phone = networkShopDetail?.phone.orEmpty()
    )
)