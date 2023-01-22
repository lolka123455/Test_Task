package com.example.testtask.detail_screen.presentation.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask.detail_screen.databinding.ItemProductImageBinding

class ProductImagesViewPagerAdapter() :
    RecyclerView.Adapter<ProductImagesViewPagerAdapter.HotSaleViewHolder>() {

    var currentList: List<String>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotSaleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductImageBinding.inflate(inflater, parent, false)
        return HotSaleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HotSaleViewHolder, position: Int) {
        Glide.with(holder.itemView).load(differ.currentList[position]).into(holder.image)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class HotSaleViewHolder(private val binding: ItemProductImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val image = binding.productImageView
    }
}