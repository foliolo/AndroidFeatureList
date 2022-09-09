package com.ahgitdevelopment.androidfeaturelist.ui.featurelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.ahgitdevelopment.androidfeaturelist.BaseFragment
import com.ahgitdevelopment.androidfeaturelist.R
import com.ahgitdevelopment.androidfeaturelist.databinding.FragmentFeatureListBinding
import com.ahgitdevelopment.androidfeaturelist.ui.featurelist.FeatureListViewModel.FeatureType.NOTIFICATION_PERMISSION_ANDROID_13

class FeatureListFragment : BaseFragment() {

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
            binding.featureListRecyclerView.apply {
                adapter = StringRecyclerAdapter(features) { featureType ->
                    when (featureType) {
                        NOTIFICATION_PERMISSION_ANDROID_13 -> {
                            findNavController().navigate(R.id.notificationPermissionFragment)
                        }
                    }
                }
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            }
        }
    }
}

