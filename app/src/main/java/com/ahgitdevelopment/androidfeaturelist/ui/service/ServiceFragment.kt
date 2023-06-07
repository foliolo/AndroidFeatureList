package com.ahgitdevelopment.androidfeaturelist.ui.service

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahgitdevelopment.androidfeaturelist.BaseFragment
import com.ahgitdevelopment.androidfeaturelist.databinding.FragmentServiceBinding
import com.ahgitdevelopment.androidfeaturelist.extensions.logD
import com.ahgitdevelopment.androidfeaturelist.services.counter.CounterService

class ServiceFragment : BaseFragment() {

    private var _binding: FragmentServiceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ServiceViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        FragmentServiceBinding.inflate(inflater, container, false).let {
            _binding = it
            return it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setListeners()
    }

    override fun onStart() {
        "onStart".logD()
        super.onStart()
        startService()
    }

    private fun startService() {
        Intent(requireContext(), CounterService::class.java).also { intent ->
            requireActivity().bindService(intent, viewModel.serviceConnectionManager, Context.BIND_AUTO_CREATE)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setListeners() = with(binding) {

        fragmentServiceStartServiceButton.setOnClickListener {
            viewModel.serviceConnectionManager.counterService.startCounter()
        }

        fragmentServiceEndServiceButton.setOnClickListener {
            viewModel.serviceConnectionManager.counterService.stopCounter()
        }

        fragmentServiceKillServiceButton.setOnClickListener {
            requireContext().unbindService(viewModel.serviceConnectionManager)
            viewModel.serviceConnectionManager.counterService.killService()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setObservers() = with(viewModel) {
        serviceConnectionManager.isConnected.observe(viewLifecycleOwner) {
            if (it) {
                serviceConnectionManager.counterService.counterManager.counter.observe(viewLifecycleOwner) { count ->
                    binding.fragmentServiceStatusText.text = "Counter: $count"
                }
            }
        }
    }
}
