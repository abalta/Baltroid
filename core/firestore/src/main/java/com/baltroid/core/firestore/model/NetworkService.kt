package com.baltroid.core.firestore.model

import com.google.firebase.firestore.DocumentId

data class NetworkService(
    @DocumentId
    val id: String? = null,
    val code: Int? = null,
    val name: String? = null,
    val icon: Int? = null
)
