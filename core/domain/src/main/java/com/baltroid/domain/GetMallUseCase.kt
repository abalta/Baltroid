package com.baltroid.domain

import com.baltroid.core.data.repository.CitiesRepository
import com.baltroid.model.Category
import com.baltroid.model.Mall
import com.baltroid.model.ShopDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetMallUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    operator fun invoke(param: String): Flow<Pair<Mall, List<Category>>> {
        return combine(
            citiesRepository.getServices(),
            citiesRepository.getShops(),
            citiesRepository.getMall(param),
            citiesRepository.getCategories()
        ) { services, shops, mall, categories ->
            mall.services.keys.forEach { key ->
                services.forEach { service ->
                    if (key == service.code) {
                        mall.services[key] = service
                    }
                }
            }
            mall.shops.keys.forEach { key ->
                shops.forEach { shop ->
                    if (key == shop.code) {
                        val shopInfo = mall.shops[key]?.shopDetail
                        mall.shops[key] = shop.apply {
                            shopDetail = shopInfo!!
                        }
                    }
                }
            }
            Pair(mall, categories)
        }
    }
}