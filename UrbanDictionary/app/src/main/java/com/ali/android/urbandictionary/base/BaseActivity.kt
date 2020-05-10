package com.ali.android.urbandictionary.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<B : ViewDataBinding> : DaggerAppCompatActivity() {

    protected lateinit var binding: B
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    protected inline fun showSnackbar(message: () -> String) {
        Snackbar.make(binding.root, message(), Snackbar.LENGTH_LONG).show()
    }
}
