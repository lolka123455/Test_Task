package com.example.testtask.main_screen.network.api.retrofitBuilder

import com.example.testtask.main_screen.network.interceptor.Interceptor.client
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainScreenRetrofitBuilder {

    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    companion object {
        const val BASE_URL = "https://run.mocky.io/v3/"
    }
}