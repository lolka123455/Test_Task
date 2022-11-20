package com.example.testtask.detail_screen.network.interceptor

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object Interceptor {

    val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
}