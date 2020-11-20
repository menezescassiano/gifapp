package com.cassianomenezes.gifapp.home.view.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cassianomenezes.gifapp.BuildConfig
import com.cassianomenezes.gifapp.home.model.GifData
import com.cassianomenezes.gifapp.repository.DataRepository
import kotlinx.coroutines.launch


class HomeListViewModel(val repository: DataRepository) : ViewModel() {

    companion object {
        const val LIMIT = "20"
        const val OFFSET = "0"
        const val RATING = "g"
        const val LANG = "en"
    }

    val responseData: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var list: GifData
    var inputTextSearch = ObservableField("")
    var running = ObservableBoolean(false)
    var showTryAgain = ObservableBoolean(false)

    init {
        getTrendingData()
    }

    fun handleData() {
        when {
            inputTextSearch.get().isNullOrEmpty() -> getTrendingData()
            else -> getData()
        }
    }

    private fun getTrendingData() {
        running.set(true)
        showTryAgain.set(false)
        viewModelScope.launch {
            try {
                repository.getTrending(getTrendingParams()).run {
                    running.set(false)
                    takeIf { this.isSuccessful }?.run {
                        responseData.postValue(true)
                        list = this.body() as GifData
                    } ?: showTryAgain.set(true)
                }
            } catch (e: Exception) {
                running.set(false)
                showTryAgain.set(true)
            }
        }
    }

    private fun getData() {
        running.set(true)
        showTryAgain.set(false)
        viewModelScope.launch {
            try {
                repository.getData(getParams()).run {
                    running.set(false)
                    takeIf { this.isSuccessful }?.run {
                        responseData.postValue(true)
                        list = this.body() as GifData
                    } ?: showTryAgain.set(true)
                }
            } catch (e: Exception) {
                running.set(false)
                showTryAgain.set(true)
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
        data["rating"] = LANG

        return data
    }
}