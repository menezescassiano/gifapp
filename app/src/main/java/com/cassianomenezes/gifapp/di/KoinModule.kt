package com.cassianomenezes.gifapp.di

import com.cassianomenezes.gifapp.home.database.GifRepository
import com.cassianomenezes.gifapp.home.view.viewmodel.BaseViewModel
import com.cassianomenezes.gifapp.home.view.viewmodel.FavListViewModel
import com.cassianomenezes.gifapp.home.view.viewmodel.HomeListViewModel
import com.cassianomenezes.gifapp.network.RetrofitClient
import com.cassianomenezes.gifapp.repository.DataRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val serviceModule = module {
    single { RetrofitClient.getApiService() }
}

private val repositoryModule = module {
    single { DataRepository(service = get()) }
}

private val viewModelModule = module {
    viewModel { BaseViewModel() }
    viewModel { (gifRepository: GifRepository) -> HomeListViewModel(repository = get(), gifRepositoryImpl = gifRepository) }
    viewModel { (gifRepository: GifRepository) -> FavListViewModel(repository = get(), gifRepositoryImpl = gifRepository) }
}

fun loadKoinModules() {
    org.koin.core.context.loadKoinModules(listOf(serviceModule, repositoryModule, viewModelModule))
}