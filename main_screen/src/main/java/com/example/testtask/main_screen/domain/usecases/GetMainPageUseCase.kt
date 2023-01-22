package com.example.testtask.main_screen.domain.usecases

import com.example.testtask.main_screen.domain.repository.MainScreenRepository

class GetMainPageUseCase(private val repository: MainScreenRepository) {

    suspend fun execute() = repository.getMainPage()
}