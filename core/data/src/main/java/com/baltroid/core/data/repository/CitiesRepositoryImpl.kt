package com.baltroid.core.data.repository

import com.baltroid.core.common.dispatcher.BaltroidDispatchers
import com.baltroid.core.common.dispatcher.Dispatcher
import com.baltroid.core.data.model.asCategory
import com.baltroid.core.data.model.asCity
import com.baltroid.core.data.model.asMall
import com.baltroid.core.data.model.asService
import com.baltroid.core.data.model.asShop
import com.baltroid.core.firestore.MallQuestFirestore
import com.baltroid.model.Category
import com.baltroid.model.City
import com.baltroid.model.Mall
import com.baltroid.model.Service
import com.baltroid.model.Shop
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CitiesRepositoryImpl @Inject constructor(
    @Dispatcher(BaltroidDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val firestore: MallQuestFirestore
): CitiesRepository {

    private val cityList = mutableListOf<City>()
    private val mallList = mutableListOf<Mall>()
    private val serviceList = mutableListOf<Service>()
    private val shopList = mutableListOf<Shop>()
    private val categoryList = mutableListOf<Category>()

    override fun getCities(): Flow<List<City>> = flow {
        if (cityList.isEmpty()) {
            cityList.addAll(firestore.getCities().map {
                it.asCity()
            })
        }
        emit(cityList)
    }.flowOn(ioDispatcher)

    override fun getMalls(): Flow<List<Mall>> = flow {
        if (mallList.isEmpty()) {
            mallList.addAll(firestore.getMalls().map {
                it.asMall()
            })
        }
        emit(mallList)
    }.flowOn(ioDispatcher)

    override fun getMall(id: String): Flow<Mall> = flow {
        emit(firestore.getMall(id)!!.asMall())
    }.flowOn(ioDispatcher)

    override fun getServices(): Flow<List<Service>> = flow {
        if (serviceList.isEmpty()) {
            serviceList.addAll(firestore.getServices().map {
                it.asService()
            })
        }
        emit(serviceList)
    }.flowOn(ioDispatcher)

    override fun getShops(): Flow<List<Shop>> = flow {
        if (shopList.isEmpty()) {
            shopList.addAll(firestore.getShops().map {
                it.asShop()
            })
        }
        emit(shopList)
    }.flowOn(ioDispatcher)

    override fun getCategories(): Flow<List<Category>> = flow {
        if (categoryList.isEmpty()) {
            categoryList.addAll(firestore.getCategories().map {
                it.asCategory()
            })
        }
        emit(categoryList)
    }.flowOn(ioDispatcher)
}