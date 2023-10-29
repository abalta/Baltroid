package com.baltroid.core.firestore.model

import com.google.firebase.firestore.DocumentId

data class NetworkCategory(
    @DocumentId
    val id: String? = null,
    val code: Int? = null,
    val name: String? = null
)
