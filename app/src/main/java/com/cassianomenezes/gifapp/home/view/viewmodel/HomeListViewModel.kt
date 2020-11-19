package com.cassianomenezes.gifapp.home.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cassianomenezes.gifapp.home.model.Gif
import com.cassianomenezes.gifapp.home.model.GifData
import com.cassianomenezes.gifapp.repository.DataRepository
import kotlinx.coroutines.launch

class HomeListViewModel(val repository: DataRepository) : ViewModel() {

    val responseData: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var list: GifData

    fun getData() {
        /*running.set(true)
        showTryAgain.set(false)*/
        viewModelScope.launch {
            try {
                repository.getData().run {
                    //running.set(false)
                    takeIf { this.isSuccessful }?.run {
                        responseData.postValue(true)
                        list = this.body() as GifData
                    } /*?: showTryAgain.set(true)*/
                }
            } catch (e: Exception) {
                print("deu ruim")
            }
        }
    }
}