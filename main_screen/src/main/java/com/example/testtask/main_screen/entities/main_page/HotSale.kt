package com.example.testtask.main_screen.entities.main_page

data class HotSale(
    val id: Int,
    val isBuy: Boolean,
    val isNew: Boolean,
    val picture: String,
    val subtitle: String,
    val title: String
)