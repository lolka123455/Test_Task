package com.example.testtask.detail_screen.usecases

import com.example.testtask.detail_screen.repository.DetailsScreenRepository

class GetProductDetailsUseCase(private val repository: DetailsScreenRepository) {

    suspend fun execute() = repository.getDetails()
}