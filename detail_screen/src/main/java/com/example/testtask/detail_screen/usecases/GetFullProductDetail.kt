package com.example.testtask.detail_screen.usecases

import com.example.testtask.detail_screen.repository.DetailScreenRepository

class GetFullProductDetail(private val repository: DetailScreenRepository) {

    suspend operator fun invoke() = repository.getDetails()
}