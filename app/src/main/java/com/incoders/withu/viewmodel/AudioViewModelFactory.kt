package com.incoders.withu.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Factory for creating the view model
@Suppress("UNCHECKED_CAST")
class AudioViewModelFactory(val application: Application): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AudioViewModel(application) as T
    }
}