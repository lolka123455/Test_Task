package com.example.testtask.detail_screen.di

import com.example.testtask.detail_screen.network.api.retrofitBuilder.DetailScreenRetrofitBuilder
import com.example.testtask.detail_screen.usecases.GetFullProductDetail
import com.example.testtask.detail_screen.viewmodels.DetailsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DetailScreenModule = module {

    single {
        DetailScreenRetrofitBuilder().provideRetrofitInstance()
    }

    factory {
       GetFullProductDetail(get())
    }

    viewModel {
        DetailsScreenViewModel(get())
    }

}