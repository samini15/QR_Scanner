package com.shayan.qrnfcscanner.livedata

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*
import com.shayan.qrnfcscanner.model.Code
import timber.log.Timber
/**
 * This class acts as a custom LiveData and will detect changes (unsing EventListener) in the current collection. (GET)
 */
/* Inspiration Source ==> https://medium.com/better-programming/using-firestore-with-android-and-architecture-components-cb3b5364027e */
class CodesListLiveData(private val collectionReference: CollectionReference) : LiveData<List<Code>>(), EventListener<QuerySnapshot> {

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
            val codes: MutableList<Code> = mutableListOf()
            for (doc in querySnapshot.documents) {
                doc.getDate("dateCreated")
                val code = doc.toObject(Code::class.java)
                if (code != null) {
                    codes.add(code)
                }
            }
            for (code in codes) {
                Timber.d("Service ==> ${code.url}")
            }
            this.value = codes
        }
    }

    private fun queryListener() : ListenerRegistration {
        return collectionReference.orderBy("url").addSnapshotListener(this)
    }
}