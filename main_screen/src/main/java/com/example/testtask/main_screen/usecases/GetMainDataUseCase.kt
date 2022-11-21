package com.example.testtask.main_screen.usecases

import com.example.testtask.main_screen.repository.MainScreenRepository

class GetMainDataUseCase(private val repository: MainScreenRepository) {

    suspend operator fun invoke() = repository.getMainData()
}