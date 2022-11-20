package com.example.testtask.cart_screen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask.cart_screen.databinding.CartItemBinding
import com.example.testtask.cart_screen.network.models.Basket

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
        val binding = CartItemBinding.inflate(inflater, parent, false)
        return CartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val item = differ.currentList[position]
        with(holder) {

            title.text = item.title
            price.text = item.price.toPriceFormat()
            count.text = item.count?.toString() ?: "1"

            Glide.with(holder.itemView).load(item.images).into(image)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size


    inner class CartItemViewHolder(
        private val binding: CartItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val title = binding.tvTitleItem
        val price = binding.tvPriceItem
        val image = binding.ivCoverItem
        val count = binding.tvTotalCount
    }

    private fun Int.toPriceFormat(): String = "$%.${2}f".format(this.toDouble())
}
