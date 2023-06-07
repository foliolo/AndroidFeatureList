package com.ahgitdevelopment.androidfeaturelist.services.counter

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.ahgitdevelopment.androidfeaturelist.R

class ServiceNotificationManager(private val context: Context) {

    lateinit var notification: Notification

    fun createNotificationChannel() = with(context) {
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

    fun createNotification(cont: Int = 0) = NotificationCompat.Builder(context, FOREGROUND_CHANNEL_ID)
        .setContentTitle("Counter Foreground Service")
        .setContentText("Running: $cont")
        .setSmallIcon(R.mipmap.ic_launcher_round)
        .setSilent(true)
        .setLights(ContextCompat.getColor(context, R.color.purple_700), 500, 500)
        .build().also {
            notification = it
        }

    fun updateNotification(cont: Int) = with(context) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotification(cont)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        const val NOTIFICATION_ID = 1
        const val FOREGROUND_CHANNEL_ID = "ForegroundServiceChannel"
    }
}
