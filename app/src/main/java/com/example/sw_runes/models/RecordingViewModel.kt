package com.example.sw_runes.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecordingViewModel : ViewModel() {

    val startRecoding: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

}