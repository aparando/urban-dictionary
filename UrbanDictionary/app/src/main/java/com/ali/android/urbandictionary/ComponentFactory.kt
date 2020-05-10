package com.ali.android.urbandictionary

import android.app.Application

class ComponentFactory private constructor() {
    companion object {
        fun create(app: Application): AppComponent {
            return DaggerAppComponent.builder().app(app).build()
        }
    }
}
