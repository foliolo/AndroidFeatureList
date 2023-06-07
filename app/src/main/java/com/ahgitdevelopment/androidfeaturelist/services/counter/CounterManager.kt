package com.ahgitdevelopment.androidfeaturelist.services.counter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay

class CounterManager {

    private val _counter = MutableLiveData(0)
    val counter: LiveData<Int> = _counter

    private var isWorking = false

    suspend fun counterJob(isWorking: Boolean) {
        this.isWorking = isWorking
        working()
    }

    fun resetCounter() {
        _counter.postValue(0)
    }

    private suspend fun working() {
        while (isWorking) {
            _counter.value?.let { cont ->
                _counter.postValue(cont + 1)
            }
            delay(500)
        }
    }
}
