package com.example.testtask.main_screen.network.api.service

import com.example.testtask.main_screen.network.models.MainPage
import retrofit2.http.GET

interface MainScreenService {

    @GET("654bd15e-b121-49ba-a588-960956b15175")
    suspend fun getMainData(): MainPage
}