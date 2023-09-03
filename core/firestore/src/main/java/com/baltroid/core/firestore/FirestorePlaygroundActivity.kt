package com.baltroid.core.firestore

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.baltroid.core.firestore.model.NetworkCity
import com.baltroid.core.firestore.model.NetworkMall
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestorePlaygroundActivity : ComponentActivity() {

    private val firestore = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mallRef = firestore.document("malls/1bt8jFVr7CmGX54LAq1i/detail/1bt8jFVr7CmGX54LAq1i").get()
        mallRef.addOnSuccessListener { result ->
            Log.d("NAME", "${result.id} => ${result.get("name")}")
        }
    }
}