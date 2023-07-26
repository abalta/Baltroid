package com.hitreads.core.domain.repository

import com.baltroid.core.common.result.BaltroidResult
import com.hitreads.core.domain.model.FavoriteModel
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(type: String, id: Int?): Flow<BaltroidResult<List<FavoriteModel>>>
    fun createFavorite(type: String, id: Int): Flow<BaltroidResult<Unit?>>
    fun deleteFavorite(type: String, id: Int): Flow<BaltroidResult<Unit?>>
}