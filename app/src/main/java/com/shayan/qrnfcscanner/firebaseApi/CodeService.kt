package com.shayan.qrnfcscanner.firebaseApi

import com.google.firebase.firestore.FirebaseFirestore
import com.shayan.qrnfcscanner.livedata.CodeLiveData
import com.shayan.qrnfcscanner.livedata.CodesListLiveData
import com.shayan.qrnfcscanner.model.Code
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CodeService @Inject constructor() {
    companion object {
        private const val CODES_COLLECTION = "codes"
    }

    private val collection = FirebaseFirestore.getInstance().collection(CODES_COLLECTION)

    fun addCode(code: Code) {
        val code = hashMapOf(
            "id" to collection.document().id,
                "dateCreated" to code.dateCreated,
                "url" to code.url
        )

        collection.add(code).addOnSuccessListener {

        }.addOnFailureListener {

        }
    }

    fun deleteCode(id: String) : CodesListLiveData {
        val documentRef = collection.document(id)

        documentRef.delete().addOnCompleteListener {
            if (it.isComplete) {
                Timber.d("Code deleted ==> ${documentRef.id} <> $id")
            } else {
                Timber.d("##### ${it.exception}")
            }
        }

        /*.delete().addOnSuccessListener {
            Timber.d("Code deleted ==> ${collection.document(id).path}")
        }.addOnFailureListener {
            Timber.d("##### ${it.message}")
        }*/

        return getAllCodes()
    }

    fun getAllCodes() : CodesListLiveData {
        return CodesListLiveData(collection)
    }

    fun getCodeById(id: String) : CodeLiveData {
        return CodeLiveData(collection, id)
    }


}