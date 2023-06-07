package com.ahgitdevelopment.androidfeaturelist.services.counter

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import com.ahgitdevelopment.androidfeaturelist.extensions.logD
import com.ahgitdevelopment.androidfeaturelist.services.counter.ServiceNotificationManager.Companion.NOTIFICATION_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CounterService : LifecycleService(), ICounterService {

    lateinit var counterManager: CounterManager

    // Binder given to clients.
    private val binder = LocalBinder()

    private val notificationManager = ServiceNotificationManager(this)

    private val serviceScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    override fun onCreate() {
        "OnCreate Service".logD()
        super.onCreate()
        counterManager = CounterManager().apply {
            counter.observe(this@CounterService) {
                notificationManager.updateNotification(it)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        "onStartCommand".logD()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        "onBind".logD()
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        "onUnbind".logD()
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        "onRebind".logD()
        super.onRebind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun startCounter() {
        "startForegroundService".logD()

        notificationManager.apply {
            createNotificationChannel()
            notificationManager.createNotification()
        }

        startForeground(NOTIFICATION_ID, notificationManager.notification)

        serviceScope.launch {
            counterManager.counterJob(true)
        }
    }

    override fun stopCounter() {
        "stopForegroundService".logD()
        serviceScope.launch {
            counterManager.counterJob(false)
        }
    }

    override fun killService() {
        "killService".logD()
        counterManager.resetCounter()
        stopForeground(STOP_FOREGROUND_REMOVE)
        serviceScope.cancel("User kills the process")
        stopSelf()
    }

    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods.
        fun getService(): CounterService = this@CounterService
    }
}
