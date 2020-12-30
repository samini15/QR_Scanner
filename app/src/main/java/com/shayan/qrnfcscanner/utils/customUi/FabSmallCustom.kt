package com.shayan.qrnfcscanner.utils.customUi

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.shayan.qrnfcscanner.R
import com.shayan.qrnfcscanner.global.App
import kotlinx.android.synthetic.main.view_fab_small.view.*

class FabSmallCustom(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    val labelView: TextView
        get() = text_view_label

    var offsetYAnimation = 0.0f

    init {
        View.inflate(context, R.layout.view_fab_small, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FabSmallCustom)
        text_view_label.text = attributes.getString(R.styleable.FabSmallCustom_name)
        fab_qr.setImageResource(attributes.getResourceId(R.styleable.FabSmallCustom_icon_src, R.mipmap.ic_launcher))
        offsetYAnimation = attributes.getDimension(R.styleable.FabSmallCustom_offset_y, offsetYAnimation)

        attributes.recycle()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        fab_qr.setOnClickListener(l)
    }
}