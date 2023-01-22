package com.example.testtask.main_screen.presentation.adapters.delegate_adapter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.main_screen.databinding.DelegateItemHotSalesViewpagerBinding
import com.example.testtask.main_screen.domain.entities.main_page.HotSale
import com.example.testtask.main_screen.presentation.adapters.delegate_adapter.DelegateAdapter
import com.example.testtask.main_screen.presentation.adapters.delegate_adapter.items.HotSalesDelegateItem
import com.example.testtask.main_screen.presentation.fragments.main.viewpager.HotSalesViewPagerAdapter

class HotSalesDelegateAdapter(
    private val hotSalesItemClickListener: (HotSale) -> Unit
) : DelegateAdapter<HotSalesDelegateItem, HotSalesDelegateAdapter.HotSalesViewHolder>(
    HotSalesDelegateItem::class.java
) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DelegateItemHotSalesViewpagerBinding.inflate(inflater, parent, false)
        return HotSalesViewHolder(binding)
    }

    override fun bindViewHolder(model: HotSalesDelegateItem, viewHolder: HotSalesViewHolder) {
        with(viewHolder) {
            progressBar.visibility = if (model.hotSales.isEmpty()) {
                View.VISIBLE
                return
            } else {
                View.GONE
            }
            viewPager.adapter = HotSalesViewPagerAdapter(
                hotSales = model.hotSales,
                itemClickListener = hotSalesItemClickListener
            )
        }
    }

    inner class HotSalesViewHolder(
        val binding: DelegateItemHotSalesViewpagerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val progressBar = binding.hotSalesProgressBar
        val viewPager = binding.hotSalesViewPager
    }
}

