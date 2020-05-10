package com.ali.android.urbandictionary.extension

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    val dividerDrawable = context.drawables[drawableRes]
    dividerItemDecoration.setDrawable(dividerDrawable)
    addItemDecoration(dividerItemDecoration)
}
