package com.cassianomenezes.gifapp.di

import com.cassianomenezes.gifapp.home.view.viewmodel.MainViewModel
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

/*private val resourceManager = module {
    single { ResourceManager(context = get()) }
}*/

private val viewModelModule = module {
    viewModel { MainViewModel(repository = get()) }
    //viewModel { RecipeDetailViewModel(resourceManager = get()) }
}

fun loadKoinModules() {
    org.koin.core.context.loadKoinModules(listOf(serviceModule, repositoryModule, /*resourceManager,*/ viewModelModule))
}