package com.example.testtask.main_screen.adapters.hotSales

import com.example.testtask.main_screen.entities.main_page.HotSale
import com.example.testtask.main_screen.adapters.delegate.DelegateAdapterItem

data class HotSalesDelegateItem(
    val hotSales: List<HotSale>
) : DelegateAdapterItem {

    override fun id(): Any = hashCode()

    override fun content(): Any = 1
}