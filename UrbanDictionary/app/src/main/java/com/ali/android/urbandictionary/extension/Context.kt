package com.ali.android.urbandictionary.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

inline fun <reified A : Activity> Context.launch(
    bundle: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<A>(init)
    startActivity(intent, bundle)
}

inline fun <reified C : Context> Context.newIntent(noinline init: Intent.() -> Unit = {}): Intent {
    val intent = Intent(this, C::class.java)
    intent.init()
    return intent
}
