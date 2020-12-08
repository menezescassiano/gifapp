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
            try {
                gifObject.run {
                    added = !added
                    when {
                        added -> gifRepositoryImpl.insertAll(this)
                        else -> gifRepositoryImpl.delete(this)
                    }
                }

                listData.run {
                    value?.find { it.id == gifObject.id }?.added = gifObject.added
                    value = listData.value
                }

            } catch (e: Exception){
                println("Error: $e")
            }
        }
    }

    fun handleList(gifObjects: ArrayList<Gif>) {
        val newGifObjects = ArrayList<GifObject>()
        viewModelScope.launch {
            try {
                gifObjects.forEach {
                    when {
                        gifRepositoryImpl.getById(it.id) != null ->
                            newGifObjects.add(GifObject(it.id, it.title, it.images.originalDetail.url, true))

                        else ->
                            newGifObjects.add(GifObject(it.id, it.title, it.images.originalDetail.url, false))

                    }
                }
                listData.value = newGifObjects
                running.set(false)
            } catch (e: Exception){
                print("Error: $e")
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