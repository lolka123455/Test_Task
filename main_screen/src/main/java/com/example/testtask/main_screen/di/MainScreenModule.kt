package com.example.testtask.main_screen.di

import com.example.testtask.main_screen.network.api.retrofitBuilder.MainScreenRetrofitBuilder
import com.example.testtask.main_screen.usecases.GetMainDataUseCase
import org.koin.dsl.module

val DetailScreenModule = module {

    single {
        MainScreenRetrofitBuilder().provideRetrofitInstance()
    }

    factory {
        GetMainDataUseCase(get())
    }

}