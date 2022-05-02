package com.bulletapps.candypricer.data

import com.bulletapps.candypricer.domain.model.Supply
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.suspendCoroutine

class FirebaseSupplyDataSource(
    firebaseFirestore: FirebaseFirestore
    ): SupplyDataSource {

    private val documentReference = firebaseFirestore.document("$COLLECTION_ROOT/$COLLECTION_LEVEL")
    private val suppliesReference = documentReference.collection(COLLECTION_SUPPLIES)


    override suspend fun createSupply(supply: Supply): Supply {
        return suspendCoroutine { continuation ->
            suppliesReference
                .add(supply)
                .addOnSuccessListener { continuation.resumeWith(Result.success(supply)) }
                .addOnFailureListener { e -> continuation.resumeWith(Result.failure(e)) }
        }
    }

    override suspend fun getSupply(id: String): Supply {
        return suspendCoroutine { continuation ->
            val
        }
    }

    override suspend fun getAllSupplies(): List<Supply> {
        TODO("Not yet implemented")
    }

    override suspend fun updateSupply(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSupply(id: String) {
        TODO("Not yet implemented")
    }


}