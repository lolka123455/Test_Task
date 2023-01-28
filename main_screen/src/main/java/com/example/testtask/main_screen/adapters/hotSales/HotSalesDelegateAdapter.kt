package com.example.testtask.main_screen.adapters.hotSales

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask.main_screen.adapters.delegate.DelegateAdapter
import com.example.testtask.main_screen.databinding.DelegateItemHotSalesViewpagerBinding
import com.example.testtask.main_screen.entities.main_page.HotSale
import com.example.testtask.main_screen.adapters.viewpager.HotSalesViewPagerAdapter

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
            if (model.hotSales.isEmpty()) {
                shimmer.showShimmer(true)
                return
            } else {
                shimmer.hideShimmer()
            }

            viewPager.adapter = HotSalesViewPagerAdapter(
                hotSales = model.hotSales,
                itemClickListener = hotSalesItemClickListener
            )
        }
    }

    inner class HotSalesViewHolder(
        binding: DelegateItemHotSalesViewpagerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val shimmer = binding.shimmerLayoutHotSales
        val viewPager = binding.hotSalesViewPager
    }
}