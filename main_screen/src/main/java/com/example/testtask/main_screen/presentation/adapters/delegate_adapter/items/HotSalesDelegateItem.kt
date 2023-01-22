package com.example.testtask.main_screen.presentation.adapters.delegate_adapter.items

import com.example.testtask.main_screen.domain.entities.main_page.HotSale
import com.example.testtask.main_screen.presentation.adapters.delegate_adapter.DelegateAdapterItem

data class HotSalesDelegateItem(
    val hotSales: List<HotSale>
) : DelegateAdapterItem {

    override fun id(): Any = hashCode()

    override fun content(): Any = 1
}