package com.ali.android.urbandictionary.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.github.ajalt.timberkt.Timber
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class ScopedAndroidViewModel(
    application: Application,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : AndroidViewModel(application), CoroutineScope {

    private val job = SupervisorJob()

    private val exceptionHandler =
        CoroutineExceptionHandler { exceptionCoroutineContext, exception ->
            Timber.e(exception) { "$exceptionCoroutineContext" }
        }

    override val coroutineContext: CoroutineContext = job + coroutineDispatcher + exceptionHandler

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
