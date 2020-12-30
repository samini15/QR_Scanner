package com.shayan.qrnfcscanner.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shayan.qrnfcscanner.injector.DaggerInjector
import com.shayan.qrnfcscanner.model.Code
import com.shayan.qrnfcscanner.repository.CodeRepository
import javax.inject.Inject

class QRCodeDetailViewModel : ViewModel() {

    @Inject
    lateinit var repository: CodeRepository

    private lateinit var qrCodeSelected: LiveData<Code>

    init {
        DaggerInjector.create().inject(this)
    }

    fun getQRCode(codeId: String) : LiveData<Code> {
        qrCodeSelected = repository.getCodeById(codeId)
        return qrCodeSelected
    }
}