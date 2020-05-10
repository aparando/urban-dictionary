package com.ali.android.urbandictionary

import dagger.Module
import dagger.Provides
import timber.log.Timber
import javax.inject.Singleton

@Module
class DevSettingsModule {
    @Provides
    @Singleton
    fun providesTree(): Timber.Tree {
        return Timber.DebugTree()
    }
}
