package com.example.testtask.main_screen.usecases

import com.example.testtask.main_screen.repository.MainScreenRepository

class GetCartUseCase(private val repository: MainScreenRepository) {

    suspend fun execute() = repository.getCart()
}