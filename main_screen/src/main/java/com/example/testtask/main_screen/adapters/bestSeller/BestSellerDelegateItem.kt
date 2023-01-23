package com.example.testtask.main_screen.adapters.bestSeller

import com.example.testtask.main_screen.entities.main_page.BestSeller
import com.example.testtask.main_screen.adapters.delegate.DelegateAdapterItem

data class BestSellerDelegateItem(
    val bestSellers: List<BestSeller>
) : DelegateAdapterItem {

    override fun id(): Any = hashCode()

    override fun content(): Any = 1
}