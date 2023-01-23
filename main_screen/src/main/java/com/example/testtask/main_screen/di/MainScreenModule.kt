package com.example.testtask.main_screen.di

import com.example.testtask.main_screen.api.MainScreenService
import com.example.testtask.main_screen.repository.MainScreenRepositoryImpl
import com.example.testtask.main_screen.repository.MainScreenRepository
import com.example.testtask.main_screen.usecases.GetCartUseCase
import com.example.testtask.main_screen.usecases.GetMainPageUseCase
import com.example.testtask.main_screen.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val MainScreenModule = module {
    single {
        provideMainScreenService(retrofit = get())
    }
    single<MainScreenRepository> {
        MainScreenRepositoryImpl(mainScreenService = get(), mainScreenDao = get())
    }
    factory {
        GetMainPageUseCase(get())
    }
    factory {
        GetCartUseCase(get())
    }
    viewModel {
        MainViewModel(get(), get())
    }
}

fun provideMainScreenService(retrofit: Retrofit): MainScreenService =
    retrofit.create(MainScreenService::class.java)