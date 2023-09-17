package com.baltroid.core.data.model

import com.baltroid.core.firestore.model.NetworkService
import com.baltroid.model.Service

fun NetworkService.asService() = Service(
    id = id.orEmpty(),
    code = code ?: 0,
    name = name.orEmpty(),
    icon = findIcon(code ?: 0)
)

fun findIcon(code: Int): Int {
    return when(code) {
        1 -> com.baltroid.core.designsystem.R.drawable.icon_atm
        2 -> com.baltroid.core.designsystem.R.drawable.icon_baby
        3 -> com.baltroid.core.designsystem.R.drawable.icon_pharmacy
        4 -> com.baltroid.core.designsystem.R.drawable.icon_electric_refueling
        5 -> com.baltroid.core.designsystem.R.drawable.icon_mosque
        6 -> com.baltroid.core.designsystem.R.drawable.icon_customer_support
        7 -> com.baltroid.core.designsystem.R.drawable.icon_car_wash
        8 -> com.baltroid.core.designsystem.R.drawable.icon_valet
        9 -> com.baltroid.core.designsystem.R.drawable.icon_wi_fi
        else -> {
            com.baltroid.core.designsystem.R.drawable.icon_wi_fi
        }
    }
}