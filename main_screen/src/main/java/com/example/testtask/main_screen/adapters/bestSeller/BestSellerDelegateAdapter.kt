package com.example.testtask.main_screen.adapters.bestSeller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.main_screen.adapters.delegateAdapter.DelegateAdapter
import com.example.testtask.main_screen.databinding.BestSellerScreenBinding
import com.example.testtask.main_screen.network.models.BestSeller

class BestSellerDelegateAdapter(private val bestSellerItemClickListener: (BestSeller) -> Unit) :
    DelegateAdapter<BestSellerDelegateItem, BestSellerDelegateAdapter.BestSellerViewHolder>(
        BestSellerDelegateItem::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BestSellerScreenBinding.inflate(inflater, parent, false)
        return BestSellerViewHolder(binding)
    }

    override fun bindViewHolder(model: BestSellerDelegateItem, viewHolder: BestSellerViewHolder) {
        with(viewHolder) {
            recyclerView.adapter = BestSellerScreenViewAdapter(
                bestSellers = model.bestSellers,
                itemClickListener = bestSellerItemClickListener
            )
        }
    }

    inner class BestSellerViewHolder(
        val binding: BestSellerScreenBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val recyclerView = binding.rvBestSeller
    }
}

