package com.example.testtask.cart_screen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.testtask.cart_screen.R
import com.example.testtask.cart_screen.databinding.ItemCartItemLayoutBinding
import com.example.testtask.cart_screen.entities.Basket
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class CartItemAdapter(
    private var cartItems: Basket
) : AbstractBindingItem<ItemCartItemLayoutBinding>() {

    override val type: Int = R.layout.item_cart_item_layout

    override var identifier: Long = cartItems.hashCode().toLong()

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemCartItemLayoutBinding =
        ItemCartItemLayoutBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemCartItemLayoutBinding, payloads: List<Any>) {
        binding.titleTextView.text = cartItems.title
        binding.priceTextView.text = cartItems.price.toPriceFormat()
        binding.itemCountTextView.text = cartItems.count?.toString() ?: "1"

        Glide.with(binding.root)
            .load(cartItems.images)
            .into(binding.itemImageView)
    }

    private fun Int.toPriceFormat(): String = "$%.${2}f".format(this.toDouble())

}