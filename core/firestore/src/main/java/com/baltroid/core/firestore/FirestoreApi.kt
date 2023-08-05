package com.baltroid.core.firestore

import com.baltroid.core.firestore.model.NetworkCity
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
    override suspend fun getCities(): List<NetworkCity> {
        val result = firestore.collection("cities").get().await()
        return result.toObjects(NetworkCity::class.java)
    }

}