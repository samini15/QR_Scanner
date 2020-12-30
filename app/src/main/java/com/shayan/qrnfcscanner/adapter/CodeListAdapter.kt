package com.shayan.qrnfcscanner.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shayan.qrnfcscanner.R
import com.shayan.qrnfcscanner.global.App
import com.shayan.qrnfcscanner.model.Code
import com.shayan.qrnfcscanner.utils.setTransitionNameCompat
import com.shayan.qrnfcscanner.utils.toFormattedString
import kotlinx.android.synthetic.main.item_code_view_holder.view.*

class CodeListAdapter(private val codes: MutableList<Code>, private val listener: ViewHolder.Listener) : RecyclerView.Adapter<CodeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_code_view_holder, parent, false), listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(codes[position])
    }

    override fun getItemCount(): Int {
        return codes.size
    }

    fun setCodes(codes: List<Code>) {
        this.codes.clear()
        this.codes.addAll(codes)
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View, listener: Listener) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        private var clickListener: Listener = listener

        init {
            this.itemView.card_view_code.setOnClickListener(this)
            this.itemView.card_view_code.setOnLongClickListener(this)
        }

        interface Listener {
            fun onCodeClick(codeSelected: Code, itemView: View)
            fun onCodeLongClick(codeSelected: Code)
        }

        var icon: ImageView = itemView.findViewById(R.id.image_view_icon)
        var url: TextView = itemView.findViewById(R.id.text_view_code_url)
        var dateCreated: TextView = itemView.findViewById(R.id.text_view_date_created)

        lateinit var code: Code

        fun bind(code: Code) {
            this.code = code

            Glide.with(App.getInstance()).load(R.drawable.ic_qrcode).fitCenter().into(icon)
            ImageViewCompat.setImageTintList(icon, ColorStateList.valueOf(ResourcesCompat.getColor(icon.context.resources, R.color.qrcode_icon, null)))

            url.text = code.url
            dateCreated.text = code.dateCreated.toFormattedString()


            setAnimations()
        }

        private fun setAnimations() {
            url.setTransitionNameCompat("url", code.id)
            icon.setTransitionNameCompat("icon", code.id)
            dateCreated.setTransitionNameCompat("date", code.id)
        }

        override fun onClick(v: View?) {
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                if (v != null) {
                    clickListener.onCodeClick(code, v)
                }
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                if (v != null) {
                    Toast.makeText(App.getInstance(), "On Long Click", Toast.LENGTH_SHORT).show()
                    clickListener.onCodeLongClick(code)
                    return true
                }
            }
            return false
        }
    }
}