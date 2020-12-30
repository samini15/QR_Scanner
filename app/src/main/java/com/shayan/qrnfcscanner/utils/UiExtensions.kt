package com.shayan.qrnfcscanner.utils

import android.view.View
import androidx.core.view.ViewCompat
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

/**
 * Extension functions for UI
 */
private val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH)
fun Timestamp.toFormattedString() : String {
    val date = this.toDate()
    return dateFormatter.format(date)
}

fun View.setTransitionNameCompat(prefix: String, id: Any) =
        ViewCompat.setTransitionName(this, "$prefix-$id")

fun View.getTransitionNameCompat() =
        ViewCompat.getTransitionName(this) ?: ""