package com.ahgitdevelopment.androidfeaturelist.services.counter

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ahgitdevelopment.androidfeaturelist.extensions.logD

class ServiceConnectionManager : ServiceConnection {

    lateinit var counterService: CounterService

    private val _isConnected = MutableLiveData(false)
    val isConnected: LiveData<Boolean> = _isConnected

    override fun onServiceConnected(className: ComponentName, service: IBinder) {
        "onServiceConnected".logD()
        // We've bound to LocalService, cast the IBinder and get LocalService instance.
        val binder = service as CounterService.LocalBinder
        counterService = binder.getService()
        _isConnected.postValue(true)
    }

    override fun onServiceDisconnected(className: ComponentName) {
        "onServiceDisconnected".logD()
        _isConnected.postValue(false)
    }

    override fun onBindingDied(name: ComponentName?) {
        "onBindingDied".logD()
        super.onBindingDied(name)
        _isConnected.postValue(false)
    }
}
