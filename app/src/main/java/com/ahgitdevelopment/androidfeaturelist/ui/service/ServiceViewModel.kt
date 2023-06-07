package com.ahgitdevelopment.androidfeaturelist.ui.service

import androidx.lifecycle.ViewModel
import com.ahgitdevelopment.androidfeaturelist.services.counter.ServiceConnectionManager

class ServiceViewModel(
    val serviceConnectionManager: ServiceConnectionManager = ServiceConnectionManager()
) : ViewModel()