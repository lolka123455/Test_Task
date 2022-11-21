package com.example.testtask.main_screen.adapters.selectCategory

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.main_screen.R
import com.example.testtask.main_screen.adapters.delegateAdapter.DelegateAdapter
import com.example.testtask.main_screen.databinding.ItemCategoriesBinding


class CategoriesDelegateAdapter(
    private val categoryItemClickListener: (CategoryItemTag) -> Unit
) :
    DelegateAdapter<CategoriesDelegateItem, CategoriesDelegateAdapter.CategoriesViewHolder>(
        CategoriesDelegateItem::class.java
    ) {

    private var categoryImageViewItems: List<ImageView>? = null

    private lateinit var selectedItemTag: CategoryItemTag

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoriesBinding.inflate(inflater, parent, false)
        setCategoryImageViewItemList(binding)
        categoryImageViewItems?.setOnClickListeners()
        return CategoriesViewHolder(binding)
    }

    private fun setCategoryImageViewItemList(binding: ItemCategoriesBinding) {
        with(binding) {
            categoryImageViewItems = listOf(
                ivPhone.apply { tag = CategoryItemTag.PHONE },
                ivPc.apply { tag = CategoryItemTag.COMPUTER },
                ivHealth.apply { tag = CategoryItemTag.HEALTH },
                ivBooks.apply { tag = CategoryItemTag.BOOKS },
            )
        }
    }

    override fun bindViewHolder(model: CategoriesDelegateItem, viewHolder: CategoriesViewHolder) {
        selectItemByTag(selectedItemTag)
    }

    fun setSelectedItem(tag: CategoryItemTag) {
        selectedItemTag = tag
        selectItemByTag(selectedItemTag)
    }

    private fun List<ImageView>.setOnClickListeners() {
        forEach { itemImageView ->
            itemImageView.setOnClickListener { itemClicked ->
                categoryItemClickListener(itemClicked.tag as CategoryItemTag)
            }
        }
    }

    private fun selectItemByTag(selectedItemTag: CategoryItemTag) {
        categoryImageViewItems?.find { it.tag == selectedItemTag }?.let { selectedItem ->
            selectItem(selectedItem)
        }
    }

    private fun selectItem(it: ImageView) {
        clearAllSelectedItems()
        setSelectedBackground(it)
    }

    private fun setUnselectedBackground(it: ImageView) {
        it.setBackgroundResource(R.drawable.round_shape_white)
        ImageViewCompat.setImageTintList(
            it,
            ColorStateList.valueOf(Color.GRAY)
        )
    }

    private fun setSelectedBackground(it: ImageView) {
        it.setBackgroundResource(R.drawable.round_shape_orange)
        ImageViewCompat.setImageTintList(
            it,
            ColorStateList.valueOf(Color.WHITE)
        )
    }

    private fun clearAllSelectedItems() {
        categoryImageViewItems?.forEach {
            setUnselectedBackground(it)
        }
    }

    inner class CategoriesViewHolder(
        val binding: ItemCategoriesBinding
    ) : RecyclerView.ViewHolder(binding.root)
}

enum class CategoryItemTag {
    BOOKS, PHONE, COMPUTER, HEALTH, UNKNOWN
}