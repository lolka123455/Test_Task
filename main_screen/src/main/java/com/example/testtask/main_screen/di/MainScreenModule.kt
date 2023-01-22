package com.example.testtask.main_screen.di

import com.example.testtask.main_screen.data.remote.MainScreenService
import com.example.testtask.main_screen.data.repository.MainScreenRepositoryImpl
import com.example.testtask.main_screen.domain.repository.MainScreenRepository
import com.example.testtask.main_screen.domain.usecases.GetCartUseCase
import com.example.testtask.main_screen.domain.usecases.GetMainPageUseCase
import com.example.testtask.main_screen.presentation.fragments.main.viewmodel.MainViewModel
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