package com.ali.android.urbandictionary.extension

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ali.android.urbandictionary.base.ScopedAndroidViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

inline fun <reified T : ViewModel> FragmentActivity.viewModel(factory: ViewModelProvider.Factory): T =
    ViewModelProviders.of(this, factory).get(T::class.java)

abstract class ContextProviderViewModel(
    application: Application,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ScopedAndroidViewModel(application, coroutineDispatcher), ContextProvider {
    override fun getContext(): Context = getApplication()
}

fun <T> LiveData<T?>.observeNotNull(owner: LifecycleOwner, block: (value: T) -> Unit) {
    observe(owner, Observer {
        if (it != null) {
            block(it)
        }
    })
}

interface ContextProvider {
    fun getContext(): Context
}
