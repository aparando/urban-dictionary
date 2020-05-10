package com.ali.android.urbandictionary.base

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ali.android.urbandictionary.BR


open class BaseViewHolder(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    @CallSuper
    open fun bind(item: Any) {
        binding.setVariable(BR.model, item)
        binding.executePendingBindings()
    }

    @CallSuper
    open fun unbind() {
        binding.unbind()
        binding.root.setOnClickListener(null)
    }
}
