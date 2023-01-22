package com.example.testtask.main_screen.presentation.adapters.delegate_adapter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.main_screen.databinding.DelegateItemSectionBinding
import com.example.testtask.main_screen.presentation.adapters.delegate_adapter.DelegateAdapter
import com.example.testtask.main_screen.presentation.adapters.delegate_adapter.items.SectionDelegateItem

class SectionDelegateAdapter :
    DelegateAdapter<SectionDelegateItem, SectionDelegateAdapter.SectionViewHolder>(
        SectionDelegateItem::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DelegateItemSectionBinding.inflate(inflater, parent, false)
        return SectionViewHolder(binding)
    }

    override fun bindViewHolder(model: SectionDelegateItem, viewHolder: SectionViewHolder) {
        viewHolder.bind(model)
    }

    inner class SectionViewHolder(
        val binding: DelegateItemSectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SectionDelegateItem) {
            with(binding) {
                sectionTitleTextView.text = item.sectionTitle
                moreTextView.text = item.moreButtonText
            }
        }
    }
}