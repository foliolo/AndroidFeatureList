package com.ahgitdevelopment.androidfeaturelist.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.ahgitdevelopment.androidfeaturelist.MainActivity
import com.ahgitdevelopment.androidfeaturelist.R
import com.ahgitdevelopment.androidfeaturelist.extensions.logD

class CustomNotification(
    private val context: Context,
    private val title: String = "Title",
    private val content: String = "Content",
    @DrawableRes private val smallIcon: Int = R.drawable.ic_launcher_foreground,
    private val importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
    private val channelId: String = "channel_id",
    private val channelName: String = "channel_name",
    private val channelDescription: String = "channel_description",
) {

    init {
        createNotificationChannel()
    }

    var builder: NotificationCompat.Builder? = null

    fun buildNotification(): Notification {

        val pendingIntent = createPendingIntent()

        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            "Notification Channel created".logD(this.javaClass.name)

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createPendingIntent(): PendingIntent {
        var intentFlags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            intentFlags = intentFlags.or(FLAG_IMMUTABLE)

        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent, intentFlags)
    }
}
