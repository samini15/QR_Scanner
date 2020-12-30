package com.shayan.qrnfcscanner.qrcode

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.shayan.qrnfcscanner.model.Code
import com.shayan.qrnfcscanner.R
import com.shayan.qrnfcscanner.viewModel.CodeListViewModel
import kotlinx.android.synthetic.main.fragment_q_r_code_scann.*

private const val PERMISSION_REQUEST_CAMERA = 1

class QRCodeScannFragment : Fragment() {

    private lateinit var viewModel: CodeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_r_code_scann, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.viewModel = ViewModelProvider(this).get(CodeListViewModel::class.java)

        view_qr_code.setResultHandler {
            Toast.makeText(context, "QR Code ==> ${it.text}", Toast.LENGTH_SHORT).show()
            viewModel.addCode(Code("", Timestamp.now(), it.text))
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()

        if (!hasCameraPermission()) {
            requestCameraPermission()
        } else {
            startCamera()
        }
    }

    override fun onPause() {
        super.onPause()

        stopCamera()
    }

    private fun startCamera() {
        view_qr_code.startCamera()
    }

    private fun stopCamera() {
        view_qr_code.stopCamera()
    }

    private fun hasCameraPermission() =
        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    private fun requestCameraPermission() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_REQUEST_CAMERA -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                findNavController().popBackStack()
            }
        }
    }

}