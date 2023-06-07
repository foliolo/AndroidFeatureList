package com.ahgitdevelopment.androidfeaturelist.ui.featurelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FeatureListViewModel : ViewModel() {
    private val _featureList = MutableLiveData<ArrayList<FeatureType>>()
    val featureList: LiveData<ArrayList<FeatureType>> = _featureList

    init {
        _featureList.postValue(
            arrayListOf(
                FeatureType.NOTIFICATION_PERMISSION_ANDROID_13,
                FeatureType.SERVICE
            )
        )
    }

    enum class FeatureType(val value: String) {
        NOTIFICATION_PERMISSION_ANDROID_13("Notification permissions on Android 13"),
        SERVICE("Service")
    }
}