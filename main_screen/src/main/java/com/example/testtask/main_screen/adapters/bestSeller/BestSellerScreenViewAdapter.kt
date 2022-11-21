package com.example.testtask.main_screen.adapters.bestSeller

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask.main_screen.R
import com.example.testtask.main_screen.databinding.ItemBestSellerBinding
import com.example.testtask.main_screen.network.models.BestSeller


class BestSellerScreenViewAdapter(
    private val bestSellers: List<BestSeller>,
    private val itemClickListener: (BestSeller) -> Unit
) : RecyclerView.Adapter<BestSellerScreenViewAdapter.BestSellerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBestSellerBinding.inflate(inflater, parent, false)
        return BestSellerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BestSellerViewHolder, position: Int) {
        val item = bestSellers[position]
        with(holder.binding) {
            cvBestSeller.setOnClickListener { itemClickListener(item) }
            tvTitleBestSeller.text = item.title
            tvPriceWithDiscount.text = item.discountPrice.toPriceFormat()
            tvPriceWithDiscount.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            tvFullPrice.text = item.priceWithoutDiscount.toPriceFormat()

            favouriteButton.setImageResource(
                if (item.isFavorites) {
                    R.drawable.ic_heart_pressed
                } else {
                    R.drawable.ic_heart_not_pressed
                }
            )
            flBestSeller.setOnClickListener { }

            Glide.with(ivBestSeller).load(item.picture).into(ivBestSeller)
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



