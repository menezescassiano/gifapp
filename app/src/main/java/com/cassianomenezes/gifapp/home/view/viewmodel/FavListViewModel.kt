package com.cassianomenezes.gifapp.home.view.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cassianomenezes.gifapp.BuildConfig
import com.cassianomenezes.gifapp.home.database.GifObject
import com.cassianomenezes.gifapp.home.database.GifRepository
import com.cassianomenezes.gifapp.home.model.GifData
import com.cassianomenezes.gifapp.repository.DataRepository
import kotlinx.coroutines.launch

class FavListViewModel(val repository: DataRepository) : BaseViewModel() {

    var gifList = ArrayList<GifObject>()
    val onDeleteGif = MutableLiveData<Boolean>()

    @RequiresApi(Build.VERSION_CODES.N)
    fun getAllElements(gifRepositoryImpl: GifRepository) {
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
                try {
                    repository.getGifsByIds(getGifsIdsParams(list)).run {
                        running.set(false)
                        takeIf { this.isSuccessful }?.run {
                            for (item in (this.body() as GifData).data) {
                                gifList.add(GifObject(item.id, item.title, item.images.originalDetail.url, true))
                            }
                            listData.value = gifList
                        } ?: showTryAgain.set(true)
                    }
                } catch (e: Exception) {
                    handleUILoading(_running = false, _showTryAgain = true)
                }
            }
        } else {
            listData.value = gifList
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getGifsIdsParams(list: List<GifObject>): MutableMap<String, String> {
        val data: MutableMap<String, String> = HashMap()
        val idsList = list.map { it.id }
        data["ids"] = idsList.toString().removePrefix("[").removeSuffix("]").replace(" ", "")
        data["api_key"] = BuildConfig.API_KEY

        return data
    }

    fun deleteGif(gifObject: GifObject, gifRepositoryImpl: GifRepository) {
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
}