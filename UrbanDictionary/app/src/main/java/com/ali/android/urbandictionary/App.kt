package com.ali.android.urbandictionary

import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class App : DaggerApplication() {

    @Inject
    lateinit var tree: Timber.Tree

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return ComponentFactory.create(this)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(tree)


        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}
