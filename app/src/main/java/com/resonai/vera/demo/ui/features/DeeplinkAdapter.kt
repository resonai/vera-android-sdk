package com.resonai.vera.demo.ui.features

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.resonai.vera.demo.R
import com.resonai.vera.demo.common.BaseAdapter
import com.resonai.vera.demo.databinding.ItemDeeplinkBinding

class DeeplinkAdapter(private var onDeeplinkClicked: ((Deeplink) -> Unit)? = null) :
    BaseAdapter<Deeplink, ItemDeeplinkBinding>(
        areItemsTheSame = { oldItem, newItem ->
            oldItem.isSelected == newItem.isSelected && oldItem.name == newItem.name
        },
        areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    ) {

    override fun inflate(inflater: LayoutInflater, parent: ViewGroup): ItemDeeplinkBinding {
        return ItemDeeplinkBinding.inflate(inflater, parent, false)
    }

    override fun ItemDeeplinkBinding.onBindItem(item: Deeplink, position: Int) {
        textView.text = item.name
        val color = if (item.isSelected) R.color.red else R.color.white
        textView.setTextColor(ContextCompat.getColor(textView.context, color))
        root.setOnClickListener {
            onDeeplinkClicked?.invoke(item)
        }
    }
}