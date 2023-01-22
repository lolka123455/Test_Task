package com.example.testtask.main_screen.presentation.adapters.delegate_adapter.items

import com.example.testtask.main_screen.domain.entities.main_page.BestSeller
import com.example.testtask.main_screen.presentation.adapters.delegate_adapter.DelegateAdapterItem

data class BestSellerDelegateItem(
    val bestSellers: List<BestSeller>
) : DelegateAdapterItem {

    override fun id(): Any = hashCode()

    override fun content(): Any = 1
}