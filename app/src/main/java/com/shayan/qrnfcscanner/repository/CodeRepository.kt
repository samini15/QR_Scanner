package com.shayan.qrnfcscanner.repository

import androidx.lifecycle.LiveData
import com.shayan.qrnfcscanner.firebaseApi.CodeService
import com.shayan.qrnfcscanner.injector.DaggerInjector
import com.shayan.qrnfcscanner.model.Code
import javax.inject.Inject

/**
 * This repository is temporary for tests.
 * It will later be refactored and communicates with a backend (Firebase)
 */
class CodeRepository @Inject constructor() {

    @Inject
    lateinit var codeService: CodeService

    init {
        DaggerInjector.create().inject(this) // Allow field injection in this class
    }

    fun getAllCodes(): LiveData<List<Code>>  = codeService.getAllCodes()

    fun getCodeById(id: String) : LiveData<Code> = codeService.getCodeById(id)

    fun insert(code: Code) {
        codeService.addCode(code)
    }

    fun delete(id: String) : LiveData<List<Code>> = codeService.deleteCode(id)

    /*fun addCode(code: Code) {
        code.id = codeId
        code.dateCreated = Date()
        codes.add(0, code)
        codeId++

        codesLiveData.value = codes
    }

    fun getQRCodeById(codeId: Int) : QRCode? {
        return findCodeById(codeId)
    }

    fun getNfcById(codeId: Int) : Nfc? {
        return findCodeById(codeId)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Code> findCodeById(codeId: Int) : T? {
        return codes.find { code -> code.id == codeId } as T
    }*/
}