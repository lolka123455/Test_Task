package com.example.testtask.cart_screen.di

import com.example.testtask.cart_screen.network.api.retrofitBuilder.CartScreenRetrofitBuilder
import com.example.testtask.cart_screen.usecases.GetCartItemsToCheckoutUseCase
import com.example.testtask.cart_screen.viewmodels.CartScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val CartScreenModule = module {

    single {
        CartScreenRetrofitBuilder().provideRetrofitInstance()
    }

    factory {
        GetCartItemsToCheckoutUseCase(get())
    }

    viewModel {
        CartScreenViewModel(get())
    }

}