package com.example.testtask.main_screen.adapters.hotSales

import com.example.testtask.main_screen.adapters.delegateAdapter.DelegateAdapterItem
import com.example.testtask.main_screen.network.models.HotSale


data class HotSalesDelegateItem(
    val hotSales: List<HotSale>
) : DelegateAdapterItem {

    override fun id(): Any = hashCode()

    override fun content(): Any = 1
}