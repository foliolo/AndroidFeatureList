package com.ahgitdevelopment.androidfeaturelist.ui.notificationpermission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.ahgitdevelopment.androidfeaturelist.BaseFragment
import com.ahgitdevelopment.androidfeaturelist.databinding.FragmentNotificationPermissionBinding
import com.ahgitdevelopment.androidfeaturelist.extensions.logD
import com.ahgitdevelopment.androidfeaturelist.notification.CustomNotification
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar

class NotificationPermissionFragment : BaseFragment() {

    private lateinit var binding: FragmentNotificationPermissionBinding
    private val viewModel: NotificationPermissionViewModel by viewModels()

    private var customNotification: CustomNotification? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
                launchNotification()
            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    private fun launchNotification() {
        // TODO: Move this logic to viewModel
        customNotification?.run {
            val notification = buildNotification()
            with(NotificationManagerCompat.from(requireContext())) {
                // notificationId is a unique int for each notification that you must define
                notify(NOTIFICATION_ID, notification)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentNotificationPermissionBinding.inflate(inflater).let { view ->
            binding = view
            view.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customNotification = CustomNotification(requireContext())

        binding.fragmentNotificationButton.setOnClickListener {

            if (Build.VERSION.SDK_INT >= 33) {
                onClickRequestPermission()
            } else {
                launchNotification()
            }
        }
    }

    @RequiresApi(33)
    fun onClickRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                "Permission Granted".logD(this.javaClass.name)
                launchNotification()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                permission
            ) -> {
                "Permission required".logD(this.javaClass.name)
                Snackbar.make(binding.root, "Permission required", LENGTH_INDEFINITE)
                    .setAction(getString(android.R.string.ok)) {
                        requestPermissionLauncher.launch(permission)
                    }.show()
            }

            else -> {
                "Permission hasn't been asked yet".logD(this.javaClass.name)
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 100

        @RequiresApi(33)
        private const val permission = Manifest.permission.POST_NOTIFICATIONS
    }
}

// https://developer.android.com/develop/ui/views/notifications/notification-permission
// https://developer.android.com/develop/ui/views/notifications/notification-permission#check-app-can-send