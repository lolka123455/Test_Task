package com.example.testtask.main_screen.di

import com.example.testtask.main_screen.network.api.retrofitBuilder.MainScreenRetrofitBuilder
import com.example.testtask.main_screen.usecases.GetMainDataUseCase
import com.example.testtask.main_screen.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DetailScreenModule = module {

    single {
        MainScreenRetrofitBuilder().provideRetrofitInstance()
    }

    factory {
        GetMainDataUseCase(get())
    }

    viewModel {
        MainViewModel(get())
    }

}