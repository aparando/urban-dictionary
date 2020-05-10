package com.ali.android.urbandictionary.base

import com.ali.android.urbandictionary.DevSettingsModule
import com.ali.android.urbandictionary.remote.NetworkModule
import com.ali.android.urbandictionary.remote.RapidApi
import com.ali.android.urbandictionary.repository.DictionaryDataSource
import com.ali.android.urbandictionary.repository.DictionaryRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DevSettingsModule::class, NetworkModule::class])
class DataModule {

    @Provides
    @Singleton
    fun provideDictionaryDataSource(
        api: RapidApi
    ): DictionaryDataSource {
        return DictionaryRepository(api)
    }
}
