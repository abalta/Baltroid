package com.baltroid.core.data.repository

import com.baltroid.core.common.dispatcher.BaltroidDispatchers
import com.baltroid.core.common.dispatcher.Dispatcher
import com.baltroid.core.data.model.asCity
import com.baltroid.core.firestore.MallQuestFirestore
import com.baltroid.model.City
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    @Dispatcher(BaltroidDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val firestore: MallQuestFirestore
): CitiesRepository {
    override fun getCities(): Flow<List<City>> = flow {
        emit(firestore.getCities().map {
            it.asCity()
        })
    }.flowOn(ioDispatcher)
}