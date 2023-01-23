package com.example.testtask.cart_screen.di

import com.example.testtask.cart_screen.data.remote.CartScreenService
import com.example.testtask.cart_screen.data.repository.CartScreenRepositoryImpl
import com.example.testtask.cart_screen.domain.repository.CartScreenRepository
import com.example.testtask.cart_screen.domain.usecases.GetCartUseCase
import com.example.testtask.cart_screen.presentation.viewmodel.CartViewModel
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