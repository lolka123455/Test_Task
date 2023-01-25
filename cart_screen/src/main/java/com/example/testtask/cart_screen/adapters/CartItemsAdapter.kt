package com.example.testtask.cart_screen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask.cart_screen.databinding.ItemCartItemLayoutBinding
import com.example.testtask.cart_screen.entities.Basket

/**
 * This is an adapter that is responsible for displaying the items in the cart.
 *
 * @property currentList the list of items in the cart
 * @property differ an AsyncListDiffer object used to handle the updates of the list of items
 * @property diffCallback a DiffUtil.ItemCallback object used to calculate the difference between two lists
 *
 * @constructor Creates a new instance of the CartItemsAdapter
 *
 * @see RecyclerView.Adapter
 *
 */

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

    /**
     *This function takes an integer value and converts it to a string in price format.
     * The format is "$%.2f", which means that the value will be displayed as a decimal with
     * two decimal places and a dollar sign at the beginning. The value is first converted to a
     * Double before being formatted.
     *
     * @return the formatted price string
     *
     * Note: This function only formats the number, it doesn't check whether the number is
     * a valid price or not.
     */

    private fun Int.toPriceFormat(): String = "$%.${2}f".format(this.toDouble())
}