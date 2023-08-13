package com.baltroid.core.firestore

import android.util.Log
import com.baltroid.core.firestore.model.NetworkCity
import com.baltroid.core.firestore.model.NetworkMall
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

private interface FirestoreApi {
    suspend fun getCities(): List<NetworkCity>
    suspend fun getMalls(): List<NetworkMall>
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
        val mallRef = firestore.collectionGroup("malls").get().await()
        return mallRef.toObjects(NetworkMall::class.java)
    }

}