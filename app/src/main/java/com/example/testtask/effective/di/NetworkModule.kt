package com.example.testtask.effective.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This package contains code related to network communication in the app.
 * It uses the OkHttp library for handling network requests, Retrofit library for making API calls and Koin library for Dependency Injection.
 *
 * @property NetworkModule a singleton Koin module that provides the HttpLoggingInterceptor, OkHttpClient, Retrofit instance and BASE_URL constant.
 *
 * @constructor Creates a new instance of the NetworkModule
 *
 * @property HttpLoggingInterceptor an interceptor that logs HTTP request and response data
 * @property OkHttpClient an HTTP client that uses the HttpLoggingInterceptor
 * @property Retrofit a REST client that uses the OkHttpClient and GsonConverterFactory
 *
 * @see okhttp3.OkHttpClient
 * @see okhttp3.logging.HttpLoggingInterceptor
 * @see org.koin.dsl.module
 * @see retrofit2.Retrofit
 * @see retrofit2.converter.gson.GsonConverterFactory
 *
 */

const val BASE_URL = "https://run.mocky.io/"

val NetworkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        OkHttpClient.Builder().addInterceptor(get() as HttpLoggingInterceptor).build()
    }
    single {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
}