package com.baltroid.core.firestore

import android.util.Log
import com.baltroid.core.firestore.model.NetworkCity
import com.baltroid.core.firestore.model.NetworkMall
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

private interface FirestoreApi {
    suspend fun getCities(): List<NetworkCity>
}

@Singleton
class MallQuestFirestore @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreApi {
    /*override suspend fun getCities(): List<NetworkCity> {
        val startTime = System.currentTimeMillis()
        val citiesCollection = firestore.collection("cities")
        val citiesRef = citiesCollection.get().await()
        val cityList = citiesRef.toObjects(NetworkCity::class.java)
        cityList.forEach { city ->
            val mallsRef =
                citiesCollection.document(city.code.toString()).collection("malls").get().await()
            val mallList = mallsRef.toObjects(NetworkMall::class.java)
            city.malls = mallList
        }
        Log.i("Firestore", (System.currentTimeMillis() - startTime).toString())
        return cityList
    }*/

    override suspend fun getCities(): List<NetworkCity> {
        val startTime = System.currentTimeMillis()
        val citiesRef = firestore.collection("cities").get().await()
        val cityList = citiesRef.toObjects(NetworkCity::class.java)

        val mallRef = firestore.collectionGroup("malls").get().await()
        val mallList = mallRef.toObjects(NetworkMall::class.java)

        mallList.forEach { mall ->
            cityList.find {
                it.code == mall.cityCode
            }?.malls?.add(mall)
        }
        Log.i("Firestore", (System.currentTimeMillis() - startTime).toString())
        return cityList
    }

}