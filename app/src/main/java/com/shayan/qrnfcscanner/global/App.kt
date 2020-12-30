package com.shayan.qrnfcscanner.global

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.shayan.qrnfcscanner.repository.CodeRepository
import timber.log.Timber
import javax.inject.Inject

class App : Application() {

    companion object {

        @JvmField
        var app: App? = null

        fun getInstance() : App {
            return app as App
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this

        Timber.plant(Timber.DebugTree())
    }
}