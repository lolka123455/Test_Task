package com.example.testtask.main_screen.adapters.bestSeller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.main_screen.adapters.delegate.DelegateAdapter
import com.example.testtask.main_screen.databinding.DelegateItemBestSellerRecyclerviewBinding
import com.example.testtask.main_screen.entities.main_page.BestSeller
import com.example.testtask.main_screen.adapters.recyclerview.BestSellerRecyclerViewAdapter

class BestSellerDelegateAdapter(private val bestSellerItemClickListener: (BestSeller) -> Unit) :
    DelegateAdapter<BestSellerDelegateItem, BestSellerDelegateAdapter.BestSellerViewHolder>(
        BestSellerDelegateItem::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DelegateItemBestSellerRecyclerviewBinding.inflate(inflater, parent, false)
        return BestSellerViewHolder(binding)
    }

    override fun bindViewHolder(model: BestSellerDelegateItem, viewHolder: BestSellerViewHolder) {
        with(viewHolder) {
            if (model.bestSellers.isEmpty()) {
                shimmer.showShimmer(true)
                return
            } else {
                shimmer.hideShimmer()
            }
            recyclerView.adapter = BestSellerRecyclerViewAdapter(
                bestSellers = model.bestSellers,
                itemClickListener = bestSellerItemClickListener
            )
        }
    }

    inner class BestSellerViewHolder(
        binding: DelegateItemBestSellerRecyclerviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val shimmer = binding.shimmerLayoutBestSeller
        val recyclerView = binding.bestSellerRecyclerView
    }
}

