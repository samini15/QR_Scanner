package com.shayan.qrnfcscanner.livedata

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*
import com.shayan.qrnfcscanner.model.Code
/**
 * This class acts as a custom LiveData and will detect changes (unsing EventListener) in the current collection more precisely the changes to a Code. (GET)
 */
/* Inspiration Source ==> https://medium.com/better-programming/using-firestore-with-android-and-architecture-components-cb3b5364027e */
class CodeLiveData (private val collectionReference: CollectionReference, private val id: String) : LiveData<Code>(), EventListener<QuerySnapshot> {

    private var listenerRegistration: ListenerRegistration? = null

    override fun onActive() {
        super.onActive()

        listenerRegistration = queryListener()
    }

    override fun onInactive() {
        super.onInactive()

        if (!hasActiveObservers()) {
            listenerRegistration?.remove()
            listenerRegistration = null
        }
    }

    override fun onEvent(querySnapshot: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (querySnapshot != null && !querySnapshot.isEmpty) {
            for (doc in querySnapshot.documents) {
                val code = doc.toObject(Code::class.java)
                if (code != null) {
                    this.value = code
                }
            }
        }
    }

    private fun queryListener() : ListenerRegistration {
        return collectionReference.whereEqualTo("id", id).addSnapshotListener(this)
    }
}