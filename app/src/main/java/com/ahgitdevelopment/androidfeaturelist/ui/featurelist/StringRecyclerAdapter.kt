package com.ahgitdevelopment.androidfeaturelist.ui.featurelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StringRecyclerAdapter(
    private val list: ArrayList<FeatureListViewModel.FeatureType>,
    private val clickListener: (FeatureListViewModel.FeatureType) -> Unit
) :
    RecyclerView.Adapter<StringRecyclerAdapter.TextViewHolder>() {

    inner class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTextView: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            android.R.layout.simple_list_item_1, parent, false
        )
        return TextViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val item = list[position]
        holder.mTextView.apply {
            text = item.value
            setOnClickListener {
                clickListener(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
