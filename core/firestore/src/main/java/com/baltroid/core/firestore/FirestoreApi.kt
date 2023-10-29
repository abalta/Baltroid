package com.baltroid.core.firestore

 import com.baltroid.core.firestore.model.NetworkCity
import com.baltroid.core.firestore.model.NetworkMall
 import com.baltroid.core.firestore.model.NetworkService
 import com.baltroid.core.firestore.model.NetworkShop
 import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

private interface FirestoreApi {
    suspend fun getCities(): List<NetworkCity>
    suspend fun getMalls():  List<NetworkMall>
    suspend fun getMall(id: String): NetworkMall?
    suspend fun getServices(): List<NetworkService>
    suspend fun getShops(): List<NetworkShop>
}

@Singleton
class MallQuestFirestore @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreApi {

    override suspend fun getCities(): List<NetworkCity> {
        val citiesRef = firestore.collection("cities").get().await()
        return citiesRef.toObjects(NetworkCity::class.java)
    }

    override suspend fun getMalls(): List<NetworkMall> {
        val mallsRef = firestore.collection("malls").get().await()
        return mallsRef.toObjects(NetworkMall::class.java)
    }

    override suspend fun getMall(id: String): NetworkMall? {
        val mallRef = firestore.document("malls/$id/detail/$id").get().await()
        return mallRef.toObject(NetworkMall::class.java)
    }

    override suspend fun getServices(): List<NetworkService> {
        val servicesRef = firestore.collection("services").get().await()
        return servicesRef.toObjects(NetworkService::class.java)
    }

    override suspend fun getShops(): List<NetworkShop> {
        val shopsRef = firestore.collection("shops").get().await()
        return shopsRef.toObjects(NetworkShop::class.java)
    }

}