package com.example.testtask.main_screen.repository

import com.example.testtask.main_screen.entities.cart.Cart
import com.example.testtask.main_screen.entities.main_page.MainPage
import com.example.testtask.state_network_connection.FetchResult

interface MainScreenRepository {

    suspend fun getMainPage(): FetchResult<MainPage>

    suspend fun getCart(): FetchResult<Cart>
}