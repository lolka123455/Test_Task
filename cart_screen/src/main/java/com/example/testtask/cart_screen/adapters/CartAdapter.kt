package com.example.testtask.cart_screen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.cart_screen.databinding.ItemCartItemLayoutBinding
import com.example.testtask.cart_screen.entities.Basket

class CartAdapter(
    private var cartItems: List<Basket> = emptyList(),
) : RecyclerView.Adapter<CartViewHolder>() {

    fun submitList(cartItems: List<Basket>) {
        this.cartItems = cartItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}