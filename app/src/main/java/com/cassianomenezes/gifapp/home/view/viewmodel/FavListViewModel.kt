package com.cassianomenezes.gifapp.home.view.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cassianomenezes.gifapp.home.database.GifObject
import com.cassianomenezes.gifapp.home.database.GifRepository
import com.cassianomenezes.gifapp.internal.RequestStatus
import com.cassianomenezes.gifapp.repository.DataRepository
import kotlinx.coroutines.launch

class FavListViewModel(val repository: DataRepository, private val gifRepositoryImpl: GifRepository) : BaseViewModel() {

    var gifList = ArrayList<GifObject>()
    val onDeleteGif = MutableLiveData<Boolean>()

    @RequiresApi(Build.VERSION_CODES.N)
    fun getAllElements() {
        viewModelScope.launch {
            try {
                getElementsGifs(gifRepositoryImpl.getAll())
            } catch (e: Exception){
                print("Error: $e")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getElementsGifs(list: List<GifObject>) {
        gifList.clear()
        if (list.isNotEmpty()) {
            handleUILoading(_running = true, _showTryAgain = false)
            viewModelScope.launch {
                repository.getGifsByIds(list).run {
                    this.data?.let {
                        for (item in it.data) {
                            gifList.add(GifObject(item.id, item.title, item.images.originalDetail.url, true))
                        }
                        listData.value = gifList
                    }
                    changeState(this.status)
                }
            }
        } else {
            listData.value = gifList
        }
    }

    fun deleteGif(gifObject: GifObject) {
        viewModelScope.launch {
            try {
                gifRepositoryImpl.delete(gifObject)
                listData.value?.remove(gifObject)
                onDeleteGif.postValue(true)
            } catch (e: Exception){
                print("Error: $e")
            }
        }
    }

    private fun changeState(state: RequestStatus) {
        when (state) {
            RequestStatus.ERROR -> handleUILoading(_running = false, _showTryAgain = true)
            else -> handleUILoading(_running = false, _showTryAgain = false)
        }
    }
}