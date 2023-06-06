package com.ahgitdevelopment.androidfeaturelist.ui.service

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahgitdevelopment.androidfeaturelist.BaseFragment
import com.ahgitdevelopment.androidfeaturelist.databinding.FragmentServiceBinding

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
        setListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun setListeners() = with(binding) {

        fragmentServiceStartServiceButton.setOnClickListener {
            fragmentServiceStatusText.text = "Start Service"
            startService()
        }

        fragmentServiceEndServiceButton.setOnClickListener {
            fragmentServiceStatusText.text = "Stop Service"
            stopService()
        }
    }

    private fun startService() {
        try {
            CounterService.getServiceIntent(
                requireContext(),
                CounterService.CountServiceAction.START_FOREGROUND.toString()
            ).let { intent ->
                requireContext().startService(intent)
            }
        } catch (ex: IllegalStateException) {
            Log.e("TAG", "Error starting the service", ex)
        }
    }

    private fun stopService() {
        try {
            CounterService.getServiceIntent(
                requireContext(),
                CounterService.CountServiceAction.STOP_SERVICE.toString()
            ).let { intent ->
                requireContext().stopService(intent)
            }

        } catch (ex: IllegalStateException) {
            Log.e("TAG", "Error starting the service", ex)
        }
    }
}