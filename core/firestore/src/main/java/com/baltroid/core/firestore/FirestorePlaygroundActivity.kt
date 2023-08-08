package com.baltroid.core.firestore

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.baltroid.core.firestore.model.NetworkCity
import com.baltroid.core.firestore.model.NetworkMall
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class FirestorePlaygroundActivity : ComponentActivity() {

    private val firestore = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val citiesRef = firestore.collection("cities")
        citiesRef.get().addOnSuccessListener { documents ->
            val cityList = documents.toObjects<NetworkCity>()
            documents.forEach { document ->
                val mallRef = citiesRef.document(document.id).collection("malls")
                mallRef.get().addOnSuccessListener { malls ->
                    val mallList = malls.toObjects<NetworkMall>()
                    val city = cityList.find {
                        it.code == document.id.toInt()
                    }
                    //city?.malls = mallList
                }
            }
            cityList.forEach {
                Log.i("Firestore", "İl ${it.name}, AVM: ${it.malls?.get(0)?.name.orEmpty()}")
            }
        }
    }
}