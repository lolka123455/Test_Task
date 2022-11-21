package com.example.testtask.main_screen.adapters.hotSales

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.main_screen.adapters.delegateAdapter.DelegateAdapter
import com.example.testtask.main_screen.databinding.ItemHotSalesViewpagerBinding
import com.example.testtask.main_screen.network.models.HotSale

class HotSalesDelegateAdapter(
    private val hotSalesItemClickListener: (HotSale) -> Unit
) : DelegateAdapter<HotSalesDelegateItem, HotSalesDelegateAdapter.HotSalesViewHolder>(
    HotSalesDelegateItem::class.java
) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHotSalesViewpagerBinding.inflate(inflater, parent, false)
        return HotSalesViewHolder(binding)
    }

    override fun bindViewHolder(model: HotSalesDelegateItem, viewHolder: HotSalesViewHolder) {
        with(viewHolder) {
            viewPager.adapter = HotSalesViewPagerAdapter(
                hotSales = model.hotSales,
                itemClickListener = hotSalesItemClickListener
            )
        }
    }

    inner class HotSalesViewHolder(
        val binding: ItemHotSalesViewpagerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val viewPager = binding.vpHotSales
    }
}

