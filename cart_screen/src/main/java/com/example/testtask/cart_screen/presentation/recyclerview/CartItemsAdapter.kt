package com.example.testtask.cart_screen.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask.cart_screen.databinding.ItemCartItemLayoutBinding
import com.example.testtask.cart_screen.domain.entities.Basket

class CartItemsAdapter : RecyclerView.Adapter<CartItemsAdapter.CartItemViewHolder>() {

    var currentList: List<Basket>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val diffCallback = object : DiffUtil.ItemCallback<Basket>() {

        override fun areItemsTheSame(oldItem: Basket, newItem: Basket): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Basket, newItem: Basket): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartItemLayoutBinding.inflate(inflater, parent, false)
        return CartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val item = differ.currentList[position]
        with(holder) {
            Glide.with(holder.itemView).load(item.images).into(image)
            title.text = item.title
            price.text = item.price.toPriceFormat()
            count.text = item.count?.toString() ?: "1"
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class CartItemViewHolder(
        private val binding: ItemCartItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val title = binding.titleTextView
        val price = binding.priceTextView
        val image = binding.itemImageView
        val count = binding.itemCountTextView
    }

    private fun Int.toPriceFormat(): String = "$%.${2}f".format(this.toDouble())
}


