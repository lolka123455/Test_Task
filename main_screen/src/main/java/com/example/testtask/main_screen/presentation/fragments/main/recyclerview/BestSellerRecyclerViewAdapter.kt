package com.example.testtask.main_screen.presentation.fragments.main.recyclerview

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask.main_screen.R
import com.example.testtask.main_screen.databinding.ItemBestSellerBinding
import com.example.testtask.main_screen.domain.entities.main_page.BestSeller

class BestSellerRecyclerViewAdapter(
    private val bestSellers: List<BestSeller>,
    private val itemClickListener: (BestSeller) -> Unit
) : RecyclerView.Adapter<BestSellerRecyclerViewAdapter.BestSellerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBestSellerBinding.inflate(inflater, parent, false)
        return BestSellerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BestSellerViewHolder, position: Int) {
        val item = bestSellers[position]
        with(holder.binding) {
            itemCardViewWithoutFavouriteButton.setOnClickListener { itemClickListener(item) }
            Glide.with(bestSellerImageView).load(item.picture).into(bestSellerImageView)
            titleBestSellerTextView.text = item.title
            priceWithDiscountTextView.text = item.discountPrice.toPriceFormat()
            priceWithDiscountTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            priceWithoutDiscountTextView.text = item.priceWithoutDiscount.toPriceFormat()
            favouriteButton.setImageResource(
                if (item.isFavorites) {
                    R.drawable.ic_fab_favorites
                } else {
                    R.drawable.ic_fab_favourites_border
                }
            )
            favouriteButtonFrame.setOnClickListener { }
        }
    }

    override fun getItemCount(): Int = bestSellers.size

    private fun Int.toPriceFormat(): String =
        if (this >= 1000) {
            "$%.${3}f".format(this / 1000.0)
        } else "$$this"

    inner class BestSellerViewHolder(val binding: ItemBestSellerBinding) :
        RecyclerView.ViewHolder(binding.root)
}



