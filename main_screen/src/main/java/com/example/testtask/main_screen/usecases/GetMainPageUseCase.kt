package com.example.testtask.main_screen.usecases

import com.example.testtask.main_screen.repository.MainScreenRepository

class GetMainPageUseCase(private val repository: MainScreenRepository) {

    suspend fun execute() = repository.getMainPage()
}