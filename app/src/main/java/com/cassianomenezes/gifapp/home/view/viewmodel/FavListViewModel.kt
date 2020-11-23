package com.cassianomenezes.gifapp.home.view.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cassianomenezes.gifapp.BuildConfig
import com.cassianomenezes.gifapp.home.database.GifObject
import com.cassianomenezes.gifapp.home.database.GifRepository
import com.cassianomenezes.gifapp.home.model.GifData
import com.cassianomenezes.gifapp.repository.DataRepository
import kotlinx.coroutines.launch

class FavListViewModel(val repository: DataRepository) : ViewModel() {

    var gifList = ArrayList<GifObject>()
    val responseData = MutableLiveData<ArrayList<GifObject>>()
    lateinit var mylist: GifData
    val running = ObservableBoolean(false)
    val showTryAgain = ObservableBoolean(false)
    val onDeleteGif = MutableLiveData<Boolean>()

    @RequiresApi(Build.VERSION_CODES.N)
    fun getAllElements(gifRepositoryImpl: GifRepository) {
        viewModelScope.launch {
            try {
                getElementsGifs(gifRepositoryImpl.getAll())
            } catch (e: Exception){
                print("awaeaeaeae$e")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getElementsGifs(list: List<GifObject>) {
        gifList.clear()
        if (list.isNotEmpty()) {
            running.set(true)
            showTryAgain.set(false)
            viewModelScope.launch {
                try {
                    repository.getGifsByIds(getGifsIdsParams(list)).run {
                        running.set(false)
                        takeIf { this.isSuccessful }?.run {
                            mylist = this.body() as GifData
                            for (item in mylist.data) {
                                gifList.add(GifObject(item.id, item.title, item.images.originalDetail.url, true))
                            }
                            responseData.value = gifList
                        } ?: showTryAgain.set(true)
                    }
                } catch (e: Exception) {
                    running.set(false)
                    showTryAgain.set(true)
                }
            }
        } else {
            responseData.value = gifList
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getGifsIdsParams(list: List<GifObject>): MutableMap<String, String> {
        val data: MutableMap<String, String> = HashMap()
        var idsList = ArrayList<String>()
        for (item in list) {
            idsList.add(item.id)
        }
        data["ids"] = idsList.toString().removePrefix("[").removeSuffix("]").replace(" ", "")
        data["api_key"] = BuildConfig.API_KEY

        return data
    }

    fun deleteGif(gifObject: GifObject, gifRepositoryImpl: GifRepository) {
        viewModelScope.launch {
            try {
                gifRepositoryImpl.delete(gifObject)
                responseData.value?.remove(gifObject)
                onDeleteGif.postValue(true)
            } catch (e: Exception){
                print("awaeaeaeae$e")
            }
        }
    }
}