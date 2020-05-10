package com.ali.android.urbandictionary.features.model

import com.ali.android.urbandictionary.R
import com.ali.android.urbandictionary.base.Item

data class DefinitionItem(
    val definition: String,
    val likesCount: String,
    val dislikesCount: String
) : Item {
    override fun getSpanSize(): Int = 1

    override fun getLayoutId(): Int = R.layout.item_definition
}
