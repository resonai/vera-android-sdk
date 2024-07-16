package com.resonai.vera.demo.ui.features

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.resonai.vera.demo.common.BaseAdapter
import com.resonai.vera.demo.databinding.ItemDeeplinkCategoryBinding

class DeeplinkCategoryAdapter : BaseAdapter<DeeplinkCategory, ItemDeeplinkCategoryBinding>(
    areItemsTheSame = { oldItem, newItem ->
        oldItem.isSelected == newItem.isSelected && oldItem.category == newItem.category
    },
    areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
) {

    private var onDeeplinkClicked: ((Deeplink) -> Unit)? = null
    private var onCategoryClicked: ((DeeplinkCategory) -> Unit)? = null

    fun setOnDeeplinkClicked(onDeeplinkClicked: ((Deeplink) -> Unit)) {
        this.onDeeplinkClicked = onDeeplinkClicked
    }

    fun setOnCategoryClicked(onCategoryClicked: ((DeeplinkCategory) -> Unit)) {
        this.onCategoryClicked = onCategoryClicked
    }

    override fun inflate(inflater: LayoutInflater, parent: ViewGroup): ItemDeeplinkCategoryBinding {
        return ItemDeeplinkCategoryBinding.inflate(inflater, parent, false)
    }

    override fun ItemDeeplinkCategoryBinding.onBindItem(item: DeeplinkCategory, position: Int) {
        imageViewArrow.scaleY = if (item.isSelected) -1f else 1f
        textView.setOnClickListener { onCategoryClicked?.invoke(item) }
        textView.text = item.category
        recyclerView.adapter = DeeplinkAdapter(onDeeplinkClicked).apply {
            submitList(item.deeplinks)
        }

        recyclerView.isVisible = item.isSelected
    }
}