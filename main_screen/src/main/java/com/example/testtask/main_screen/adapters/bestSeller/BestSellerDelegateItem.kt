package com.example.testtask.main_screen.adapters.bestSeller

import com.example.testtask.main_screen.adapters.delegateAdapter.DelegateAdapterItem
import com.example.testtask.main_screen.network.models.BestSeller


data class BestSellerDelegateItem(
    val bestSellers: List<BestSeller>
) : DelegateAdapterItem {

    override fun id(): Any = hashCode()

    override fun content(): Any = 1
}