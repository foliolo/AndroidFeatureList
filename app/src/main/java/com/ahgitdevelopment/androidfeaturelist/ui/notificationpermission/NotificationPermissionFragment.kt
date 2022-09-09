package com.ahgitdevelopment.androidfeaturelist.ui.notificationpermission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahgitdevelopment.androidfeaturelist.BaseFragment
import com.ahgitdevelopment.androidfeaturelist.databinding.FragmentNotificationPermissionBinding
import com.ahgitdevelopment.androidfeaturelist.notification.CustomNotification

class NotificationPermissionFragment : BaseFragment() {

    private lateinit var binding: FragmentNotificationPermissionBinding
    private val viewModel: NotificationPermissionViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentNotificationPermissionBinding.inflate(inflater).let { view ->
            binding = view
            view.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(false) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            })

        binding.fragmentNotificationButton.setOnClickListener {

            // TODO: Move this logic to viewModel
            CustomNotification(requireContext()).run {
                val notification = buildNotification()

                with(NotificationManagerCompat.from(requireContext())) {
                    // notificationId is a unique int for each notification that you must define
                    notify(NOTIFICATION_ID, notification)
                }
            }
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 100
    }
}