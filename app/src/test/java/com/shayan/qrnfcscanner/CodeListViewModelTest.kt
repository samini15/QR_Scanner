package com.shayan.qrnfcscanner

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shayan.qrnfcscanner.viewModel.CodeListViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class CodeListViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRole = InstantTaskExecutorRule()

    @Test
    fun testLiveData() {
        val viewModel = CodeListViewModel()
        val observer = viewModel.getCodes().observerTest()

        //assertEquals(observer.observedValues, viewModel.getCodes())
    }
}