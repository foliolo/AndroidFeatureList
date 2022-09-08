package com.ahgitdevelopment.androidfeaturelist.ui.featurelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.ahgitdevelopment.androidfeaturelist.databinding.FragmentFeatureListBinding
import com.ahgitdevelopment.androidfeaturelist.ui.featurelist.FeatureListViewModel.FeatureType
import com.ahgitdevelopment.androidfeaturelist.ui.featurelist.FeatureListViewModel.FeatureType.*

class FeatureListFragment : Fragment() {

    private lateinit var binding: FragmentFeatureListBinding
    private val viewModel: FeatureListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFeatureListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
    }

    private fun setObservers() = with(viewModel) {
        featureList.observe(viewLifecycleOwner) { features ->
            binding.featureListRecyclerView.adapter = RecyclerAdapter(features) { featureType ->
                when (featureType) {
                    NOTIFICATION_PERMISSION_ANDROID_13 -> {
                        Toast.makeText(requireContext(), "permission", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    class RecyclerAdapter(private val list: ArrayList<FeatureType>, private val clickListener: (FeatureType) -> Unit) :
        RecyclerView.Adapter<RecyclerAdapter.TextViewHolder>() {

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
}