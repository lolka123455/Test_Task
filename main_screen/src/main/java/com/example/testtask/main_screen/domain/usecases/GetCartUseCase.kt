package com.example.testtask.main_screen.domain.usecases

import com.example.testtask.main_screen.domain.repository.MainScreenRepository

class GetCartUseCase(private val repository: MainScreenRepository) {

    suspend fun execute() = repository.getCart()
}