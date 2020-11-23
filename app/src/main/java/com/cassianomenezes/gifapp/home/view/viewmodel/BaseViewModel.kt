package com.cassianomenezes.gifapp.home.view.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cassianomenezes.gifapp.home.database.GifObject

open class BaseViewModel : ViewModel() {

    val listData = MutableLiveData<ArrayList<GifObject>>()
    val running = ObservableBoolean(false)
    val showTryAgain = ObservableBoolean(false)

    fun handleUILoading(_running: Boolean, _showTryAgain: Boolean) {
        running.set(_running)
        showTryAgain.set(_showTryAgain)
    }

}