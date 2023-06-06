package com.ahgitdevelopment.androidfeaturelist.ui.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.ahgitdevelopment.androidfeaturelist.R

class CounterService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {
            CountServiceAction.START_FOREGROUND.toString() -> startForegroundService()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun startForegroundService() {
        createNotificationChannel()

        val notification: Notification = NotificationCompat.Builder(this, FOREGROUND_CHANNEL_ID)
            .setContentTitle("Counter Foreground Service")
            .setContentText("Running")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()

        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                FOREGROUND_CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    enum class CountServiceAction {
        START_FOREGROUND,
        START_BACKGROUND,
        STOP_SERVICE
    }

    companion object {
        private const val FOREGROUND_CHANNEL_ID = "ForegroundServiceChannel"

        fun getServiceIntent(context: Context, actionType: String) = Intent(context, CounterService::class.java).apply {
            action = actionType
        }
    }
}