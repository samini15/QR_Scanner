package com.shayan.qrnfcscanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class ObserverTest<T> : Observer<T> {

    val observedValues = mutableListOf<T?>()
    override fun onChanged(t: T) {
        observedValues.add(t)
    }
}

fun <T> LiveData<T>.observerTest() = ObserverTest<T>().apply { observeForever(this) }