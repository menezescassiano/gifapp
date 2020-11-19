package com.cassianomenezes.gifapp.home.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cassianomenezes.gifapp.repository.DataRepository
import kotlinx.coroutines.launch

class MainViewModel(val repository: DataRepository) : ViewModel() {

}