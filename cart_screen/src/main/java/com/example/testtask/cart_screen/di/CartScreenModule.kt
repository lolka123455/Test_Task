package com.example.testtask.cart_screen.di

import com.example.testtask.cart_screen.api.CartScreenService
import com.example.testtask.cart_screen.repository.CartScreenRepositoryImpl
import com.example.testtask.cart_screen.repository.CartScreenRepository
import com.example.testtask.cart_screen.usecases.GetCartUseCase
import com.example.testtask.cart_screen.viewmodel.CartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val CartScreenModule = module {
    single {
        provideCartScreenService(retrofit = get())
    }
    single<CartScreenRepository> {
        CartScreenRepositoryImpl(cartScreenService = get(), cartScreenDao = get())
    }
    factory {
        GetCartUseCase(get())
    }
    viewModel {
        CartViewModel(get())
    }
}

fun provideCartScreenService(retrofit: Retrofit): CartScreenService =
    retrofit.create(CartScreenService::class.java)