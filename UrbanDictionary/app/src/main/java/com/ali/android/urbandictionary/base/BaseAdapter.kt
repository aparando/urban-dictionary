package com.ali.android.urbandictionary.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

open class BaseAdapter<I : Item> : RecyclerView.Adapter<BaseViewHolder>() {

    private val items = mutableListOf<I>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].getLayoutId()

    override fun onViewRecycled(holder: BaseViewHolder) {
        holder.unbind()
    }

    fun addAll(items: List<I>) {
        val positionStart = items.size + 1
        this.items.addAll(items)
        notifyItemRangeInserted(positionStart, items.size)
    }

    fun getItem(position: Int): I = items[position]

    //TODO: Better to use DiffUtil
    fun update(items: List<I>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItems(): List<I> = Collections.unmodifiableList(items)
}
