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

    override suspend fun getSupply(id: String): Supply? {
        return suspendCoroutine { continuation ->
            suppliesReference.get().addOnSuccessListener { documents ->
                //todo: use query
                val supply = documents.map { it.toObject(Supply::class.java) }.find { it.id == id }
                continuation.resumeWith(Result.success(supply))
            }
                .addOnFailureListener { e -> continuation.resumeWith(Result.failure(e)) }
        }
    }

    override suspend fun getAllSupplies(): List<Supply> {
        return suspendCoroutine { continuation ->
            suppliesReference.get().addOnSuccessListener { documents ->
                val supplies = documents.map { it.toObject(Supply::class.java) }
                continuation.resumeWith(Result.success(supplies))
            }
                .addOnFailureListener { e -> continuation.resumeWith(Result.failure(e)) }
        }
    }

    override suspend fun updateSupply(supply: Supply) : Supply {
        return suspendCoroutine { continuation ->
            suppliesReference.document(supply.id).set(supply)
                .addOnSuccessListener { continuation.resumeWith(Result.success(supply)) }
                .addOnFailureListener { e -> continuation.resumeWith(Result.failure(e)) }
        }
    }

    override suspend fun deleteSupply(id: String) {
        return suspendCoroutine { continuation ->
            suppliesReference.document(id).delete()
                .addOnFailureListener { e -> continuation.resumeWith(Result.failure(e)) }
        }
    }


}