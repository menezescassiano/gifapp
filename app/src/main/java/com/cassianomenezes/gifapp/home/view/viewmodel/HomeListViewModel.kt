package com.cassianomenezes.gifapp.home.view.viewmodel

import androidx.lifecycle.*
import com.cassianomenezes.gifapp.home.database.GifObject
import com.cassianomenezes.gifapp.home.database.GifRepository
import com.cassianomenezes.gifapp.home.model.DataResult
import com.cassianomenezes.gifapp.home.model.Gif
import com.cassianomenezes.gifapp.home.model.GifData
import com.cassianomenezes.gifapp.internal.RequestStatus
import com.cassianomenezes.gifapp.repository.DataRepository
import kotlinx.coroutines.launch


class HomeListViewModel(val repository: DataRepository, private val gifRepositoryImpl: GifRepository) : BaseViewModel(), LifecycleObserver {

    val responseData: MutableLiveData<Boolean> = MutableLiveData()
    var list: GifData? = null
    var inputTextSearch = MutableLiveData("")

    fun handleData() {
        when {
            inputTextSearch.value.isNullOrEmpty() -> getTrendingData()
            else -> getData()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun getTrendingData() {
        handleUILoading(_running = true, _showTryAgain = false)
        viewModelScope.launch {
            repository.getTrending().run {
                handleServerResponse(this)
            }
        }
    }

    private fun getData() {
        handleUILoading(_running = true, _showTryAgain = false)
        viewModelScope.launch {
            repository.getData(inputTextSearch.value.toString()).run {
                handleServerResponse(this)
            }
        }
    }

    private fun handleServerResponse(dataResult: DataResult<GifData>) {
        responseData.postValue(true)
        dataResult.data?.let {
            list = it
        }
        changeState(dataResult.status)
    }

    fun gifCrud(gifObject: GifObject) {
        viewModelScope.launch {
            gifRepositoryImpl.gifCrud(gifObject).run {
                listData.run {
                    value?.find { it.id == gifObject.id }?.added = gifObject.added
                    value = listData.value
                }
            }
        }
    }

    fun handleList(gifObjects: ArrayList<Gif>) {
        viewModelScope.launch {
            gifRepositoryImpl.getMyData(gifObjects).run {
                listData.value = this.data
                running.set(false)
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