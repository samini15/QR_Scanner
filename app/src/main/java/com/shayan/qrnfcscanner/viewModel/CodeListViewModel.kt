package com.shayan.qrnfcscanner.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shayan.qrnfcscanner.injector.DaggerInjector
import com.shayan.qrnfcscanner.model.Code
import com.shayan.qrnfcscanner.repository.CodeRepository
import javax.inject.Inject

class CodeListViewModel : ViewModel() {

    @Inject
    lateinit var repository: CodeRepository

    private var codes : LiveData<List<Code>>

    private var isFabMenuOpen = false

    init {
        DaggerInjector.create().inject(this) // Allow field injection in this class
        this.codes = repository.getAllCodes()
    }

    fun getCodes() : LiveData<List<Code>> {
        //codes = repository.getAllCodes()
        return codes
    }

    fun addCode(code: Code) {
        repository.insert(code)
    }

    fun removeCode(id: String) : LiveData<List<Code>> {
        return repository.delete(id)
    }

    fun toggleFabMenu() {
        isFabMenuOpen = !isFabMenuOpen
    }

    fun isFabMenuOpen() : Boolean {
        return isFabMenuOpen
    }
}