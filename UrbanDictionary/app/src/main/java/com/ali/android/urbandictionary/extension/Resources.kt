package com.ali.android.urbandictionary.extension

import android.content.Context
import android.content.res.Resources
import androidx.annotation.AnyRes
import androidx.core.content.ContextCompat

class ResourceMapper<out T>(private val mapRes: (resId: Int) -> T) {
    operator fun get(@AnyRes resId: Int) = mapRes(resId)
}

// From: https://android.jlelse.eu/extending-resources-61134cbd809c
val Context.strings
    get() = ResourceMapper { getString(it) }

val Context.drawables
    // !! fail-fast, we should always have the drawable
    get() = ResourceMapper { ContextCompat.getDrawable(this, it)!! }

val Context.formattedStrings
    get() = ResourceMapper { FormattedString(resources, it) }

class FormattedString(
    private val resources: Resources,
    private val resId: Int
) {
    operator fun invoke(value1: Any?): String =
        resources.getString(resId, value1)

    operator fun invoke(value1: Any?, value2: Any?): String =
        resources.getString(resId, value1, value2)

    operator fun invoke(value1: Any?, value2: Any?, value3: Any?): String =
        resources.getString(resId, value1, value2, value3)
}
