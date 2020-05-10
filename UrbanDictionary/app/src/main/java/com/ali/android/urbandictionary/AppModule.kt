package com.ali.android.urbandictionary

import android.app.Application
import android.content.Context
import com.ali.android.urbandictionary.base.DictionaryDispatchers
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: Application): Context = app.baseContext

    @Provides
    @Singleton
    fun provideDictionaryDispatchers() = DictionaryDispatchers(
        ui = Dispatchers.Main,
        io = Dispatchers.IO
    )
}
