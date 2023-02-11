package com.example.testtask.cart_screen.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask.cart_screen.databinding.ItemCartItemLayoutBinding
import com.example.testtask.cart_screen.entities.Basket

class CartViewHolder constructor(
    private val binding: ItemCartItemLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(basket: Basket) {
        binding.titleTextView.text = basket.title
        binding.priceTextView.text = basket.price.toPriceFormat()
        binding.itemCountTextView.text = basket.count?.toString() ?: "1"

        Glide.with(binding.root).load(basket.images).into(binding.itemImageView)
    }

    private fun Int.toPriceFormat(): String = "$%.${2}f".format(this.toDouble())
}