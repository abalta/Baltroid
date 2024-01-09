package com.baltroid.model

data class Shop(
    val code: Int,
    val name: String,
    val categoryCode: Int,
    val logo: String,
    var shopDetail: ShopDetail
)

 data class ShopDetail(
     val floor: Int,
     val phone: String,
     val code: Int
 )

