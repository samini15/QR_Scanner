package com.shayan.qrnfcscanner.qrcode

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.shayan.qrnfcscanner.R
import com.shayan.qrnfcscanner.global.App
import com.shayan.qrnfcscanner.model.Code
import com.shayan.qrnfcscanner.utils.setTransitionNameCompat
import com.shayan.qrnfcscanner.utils.toFormattedString
import com.shayan.qrnfcscanner.viewModel.QRCodeDetailViewModel
import kotlinx.android.synthetic.main.fragment_q_r_code_detail.*

class QRCodeDetailFragment : Fragment() {

    private lateinit var viewModel: QRCodeDetailViewModel
    private lateinit var codeId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transition = TransitionInflater.from(context).inflateTransition(R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_q_r_code_detail, container, false)

        codeId = QRCodeDetailFragmentArgs.fromBundle(requireArguments()).codeId


        view.findViewById<TextView>(R.id.text_view_code_url).setTransitionNameCompat("url", codeId)
        view.findViewById<ImageView>(R.id.image_view_icon).setTransitionNameCompat("icon", codeId)
        view.findViewById<TextView>(R.id.text_view_date_created).setTransitionNameCompat("date", codeId)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QRCodeDetailViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getQRCode(codeId).observe(viewLifecycleOwner, {
            updateUI(it)
        })

    }

    private fun updateUI(it: Code?) {
        text_view_code_url.text = it?.url
        Glide.with(App.getInstance()).load(R.drawable.ic_qrcode).fitCenter().into(image_view_icon)
        // TODO Date update
        text_view_date_created.text = it?.dateCreated?.toFormattedString()
    }

}