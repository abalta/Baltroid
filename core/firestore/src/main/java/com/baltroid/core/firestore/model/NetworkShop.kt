package com.baltroid.core.firestore.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class NetworkShop(
    @DocumentId
    val id: String? = null,
    val code: Int? = null,
    val name: String? = null,
    @PropertyName("category_code")
    val categoryCode: Int? = null
)
