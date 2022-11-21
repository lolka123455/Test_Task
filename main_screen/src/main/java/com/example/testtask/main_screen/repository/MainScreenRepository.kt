package com.example.testtask.main_screen.repository

import com.example.testtask.main_screen.network.models.MainPage

interface MainScreenRepository {

    suspend fun getMainData(): MainPage
}