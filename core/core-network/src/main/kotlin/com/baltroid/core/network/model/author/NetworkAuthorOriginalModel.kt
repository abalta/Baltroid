package com.baltroid.core.network.model.author

import com.baltroid.core.network.model.originals.IndexNetworkOriginal

data class NetworkAuthorOriginalModel(
    val count: Int?,
    val posts: List<IndexNetworkOriginal>?
)
