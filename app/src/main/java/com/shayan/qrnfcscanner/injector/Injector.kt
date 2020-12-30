package com.shayan.qrnfcscanner.injector

import com.shayan.qrnfcscanner.repository.CodeRepository
import com.shayan.qrnfcscanner.viewModel.CodeListViewModel
import com.shayan.qrnfcscanner.viewModel.QRCodeDetailViewModel
import dagger.Component

@Component
interface Injector {

    /** Repositories */
    fun inject(codeRepository: CodeRepository)

    /** ViewModels */
    fun inject(codeListViewModel: CodeListViewModel)
    fun inject(qrCodeDetailViewModel: QRCodeDetailViewModel)
}