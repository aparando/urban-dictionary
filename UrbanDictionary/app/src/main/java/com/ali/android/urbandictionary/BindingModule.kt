package com.ali.android.urbandictionary

import com.ali.android.urbandictionary.base.ActivityScope
import com.ali.android.urbandictionary.features.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}
