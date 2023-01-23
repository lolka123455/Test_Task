package com.example.testtask.detail_screen.di

import com.example.testtask.detail_screen.api.DetailsScreenService
import com.example.testtask.detail_screen.repository.DetailsScreenRepositoryImpl
import com.example.testtask.detail_screen.repository.DetailsScreenRepository
import com.example.testtask.detail_screen.usecases.GetProductDetailsUseCase
import com.example.testtask.detail_screen.viewmodel.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val DetailsScreenModule = module {
    single {
        provideDetailsScreenService(retrofit = get())
    }
    single<DetailsScreenRepository> {
        DetailsScreenRepositoryImpl(detailsScreenService = get(), detailsScreenDao = get())
    }
    factory {
        GetProductDetailsUseCase(get())
    }
    viewModel {
        DetailsViewModel(get())
    }
}

fun provideDetailsScreenService(retrofit: Retrofit): DetailsScreenService =
    retrofit.create(DetailsScreenService::class.java)