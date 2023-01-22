package com.example.testtask.main_screen.presentation.adapters.delegate_adapter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.main_screen.databinding.DelegateItemBestSellerRecyclerviewBinding
import com.example.testtask.main_screen.domain.entities.main_page.BestSeller
import com.example.testtask.main_screen.presentation.adapters.delegate_adapter.DelegateAdapter
import com.example.testtask.main_screen.presentation.adapters.delegate_adapter.items.BestSellerDelegateItem
import com.example.testtask.main_screen.presentation.fragments.main.recyclerview.BestSellerRecyclerViewAdapter

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
            progressBar.visibility = if (model.bestSellers.isEmpty()) {
                View.VISIBLE
                return
            } else {
                View.GONE
            }
            recyclerView.adapter = BestSellerRecyclerViewAdapter(
                bestSellers = model.bestSellers,
                itemClickListener = bestSellerItemClickListener
            )
        }
    }

    inner class BestSellerViewHolder(
        val binding: DelegateItemBestSellerRecyclerviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val progressBar = binding.bestSellerProgressBar
        val recyclerView = binding.bestSellerRecyclerView
    }
}

