package com.cassianomenezes.gifapp.home.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cassianomenezes.gifapp.repository.DataRepository
import kotlinx.coroutines.launch

class MainViewModel(val repository: DataRepository) : ViewModel() {

    fun getData() {
        /*running.set(true)
        showTryAgain.set(false)*/
        viewModelScope.launch {
            try {
                repository.getData().run {
                    //running.set(false)
                    takeIf { this.isSuccessful }?.run {
                        /*responseData.postValue(true)
                        list = this.body() as ArrayList<Recipe>*/
                    } /*?: showTryAgain.set(true)*/
                }
            } catch (e: Exception) {
                //showTryAgain.set(true)
            }
        }
    }
}