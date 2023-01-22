package com.example.testtask.detail_screen.domain.usecases

import com.example.testtask.detail_screen.domain.repository.DetailsScreenRepository

class GetProductDetailsUseCase(private val repository: DetailsScreenRepository) {

    suspend fun execute() = repository.getDetails()
}