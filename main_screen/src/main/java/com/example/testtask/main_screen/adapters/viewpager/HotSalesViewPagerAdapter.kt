package com.example.testtask.main_screen.adapters.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask.main_screen.databinding.ItemHotSaleBinding
import com.example.testtask.main_screen.entities.main_page.HotSale

class HotSalesViewPagerAdapter(
    private val hotSales: List<HotSale>,
    private val itemClickListener: (HotSale) -> Unit
) : RecyclerView.Adapter<HotSalesViewPagerAdapter.HotSaleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotSaleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHotSaleBinding.inflate(inflater, parent, false)
        return HotSaleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HotSaleViewHolder, position: Int) {
        val item = hotSales[position]
        with(holder.binding) {
            holder.itemView.setOnClickListener { itemClickListener(item) }

            titleHotSaleTextView.text = item.title
            subtitleTextView.text = item.subtitle
            newTagContainer.visibility = if (item.isNew) View.VISIBLE else View.INVISIBLE

            Glide.with(hotSaleImageView)
                .load(item.picture)
                .override(600, 400)
                .into(hotSaleImageView)
        }
    }

    override fun getItemCount(): Int = hotSales.size

    inner class HotSaleViewHolder(val binding: ItemHotSaleBinding) :
        RecyclerView.ViewHolder(binding.root)
}