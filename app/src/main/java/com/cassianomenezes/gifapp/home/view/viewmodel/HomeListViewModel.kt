package com.cassianomenezes.gifapp.home.view.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.cassianomenezes.gifapp.BuildConfig
import com.cassianomenezes.gifapp.home.database.GifObject
import com.cassianomenezes.gifapp.home.database.GifRepository
import com.cassianomenezes.gifapp.home.model.Gif
import com.cassianomenezes.gifapp.home.model.GifData
import com.cassianomenezes.gifapp.repository.DataRepository
import kotlinx.coroutines.launch
import kotlin.collections.set


class HomeListViewModel(val repository: DataRepository) : BaseViewModel(), LifecycleObserver {

    companion object {
        const val LIMIT = "20"
        const val OFFSET = "0"
        const val RATING = "g"
        const val LANG = "en"
    }


    val responseData: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var list: GifData
    var inputTextSearch = ObservableField("")

    fun handleData() {
        when {
            inputTextSearch.get().isNullOrEmpty() -> getTrendingData()
            else -> getData()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun getTrendingData() {
        handleUILoading(_running = true, _showTryAgain = false)
        viewModelScope.launch {
            try {
                repository.getTrending(getTrendingParams()).run {
                    takeIf { this.isSuccessful }?.run {
                        responseData.postValue(true)
                        list = this.body() as GifData
                    } ?: handleUILoading(_running = false, _showTryAgain = true)
                }
            } catch (e: Exception) {
                handleUILoading(_running = false, _showTryAgain = true)
            }
        }
    }

    private fun getData() {
        handleUILoading(_running = true, _showTryAgain = false)
        viewModelScope.launch {
            try {
                repository.getData(getParams()).run {
                    takeIf { this.isSuccessful }?.run {
                        responseData.postValue(true)
                        list = this.body() as GifData
                    } ?: handleUILoading(_running = false, _showTryAgain = true)
                }
            } catch (e: Exception) {
                handleUILoading(_running = false, _showTryAgain = true)
            }
        }
    }

    private fun getParams(): MutableMap<String, String> {
        val data: MutableMap<String, String> = HashMap()
        data["api_key"] = BuildConfig.API_KEY
        inputTextSearch.get()?.let { data["q"] = it }
        data["limit"] = LIMIT
        data["offset"] = OFFSET
        data["rating"] = RATING
        data["lang"] = LANG

        return data
    }

    private fun getTrendingParams(): MutableMap<String, String> {
        val data: MutableMap<String, String> = HashMap()
        data["api_key"] = BuildConfig.API_KEY
        data["limit"] = LIMIT
        data["rating"] = LANG

        return data
    }

    fun saveGif(gifObject: GifObject, gifRepositoryImpl: GifRepository) {
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

    fun handleList(gifObjects: ArrayList<Gif>, gifRepositoryImpl: GifRepository) {
        val newGifObjects = ArrayList<GifObject>()
        viewModelScope.launch {
            try {
                for (gifObject in gifObjects) {
                    gifObject.run {
                        when {
                            gifRepositoryImpl.getById(gifObject.id) != null ->
                                newGifObjects.add(GifObject(id, title, images.originalDetail.url, true))

                            else ->
                                newGifObjects.add(GifObject(id, title, images.originalDetail.url, false))

                        }
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
}