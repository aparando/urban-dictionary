package com.ali.android.urbandictionary.features

import android.app.AlertDialog
import android.app.SearchManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ali.android.urbandictionary.R
import com.ali.android.urbandictionary.base.BaseActivity
import com.ali.android.urbandictionary.base.BaseAdapter
import com.ali.android.urbandictionary.databinding.ActivityMainBinding
import com.ali.android.urbandictionary.extension.observeNotNull
import com.ali.android.urbandictionary.extension.plusAssign
import com.ali.android.urbandictionary.extension.setDivider
import com.ali.android.urbandictionary.extension.strings
import com.ali.android.urbandictionary.extension.viewModel
import com.ali.android.urbandictionary.features.model.DefinitionItem
import com.ali.android.urbandictionary.features.model.ViewState
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val compositeDisposable = CompositeDisposable()

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        viewModel<MainViewModel>(factory)
    }

    private val searchAdapter = BaseAdapter<DefinitionItem>()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.appBar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayShowHomeEnabled(false)
            setDisplayHomeAsUpEnabled(false)
        }
        binding.definitionList.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setDivider(R.drawable.list_divider)
        }
        viewModel.definitionsLiveData.observeNotNull(this) { viewState ->
            when (viewState) {
                is ViewState.GetDefinitionsItemsSuccess -> {
                    binding.apply {
                        progressSpinner.isVisible = false
                        emptyMessage.isVisible = false
                        definitionList.isVisible = true
                    }
                    searchAdapter.update(viewState.definitionResult.map {
                        DefinitionItem(
                            definition = it.definition,
                            likesCount = "${it.likesCount}",
                            dislikesCount = "${it.dislikesCount}"
                        )
                    })
                }
                ViewState.GetDefinitionItemsFailed -> {
                    binding.apply {
                        progressSpinner.isVisible = false
                        emptyMessage.isVisible = false
                        definitionList.isVisible = false
                    }
                    showSnackbar { strings[R.string.fetch_definitions_failed_message] }
                }
                ViewState.GetDefinitionItemsEmpty -> {
                    binding.apply {
                        progressSpinner.isVisible = false
                        emptyMessage.isVisible = true
                        definitionList.isVisible = false
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchManager = getSystemService<SearchManager>()
        val searchView = menu.findItem(R.id.menu_item_search).actionView as SearchView
        searchView.setSearchableInfo(
            searchManager?.getSearchableInfo(
                componentName
            )
        )
        compositeDisposable += searchView.queryTextChanges()
            .skipInitialValue().filter { it.isNotEmpty() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.progressSpinner.isVisible = true
                viewModel.getDefinitions(it.toString())
                searchView.clearFocus()
            }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_sort) {
            val dialogBuilder = AlertDialog.Builder(this@MainActivity)
            dialogBuilder.apply {
                setTitle( strings[R.string.sort_by])
                setIcon(R.drawable.ic_sort)
                setItems(
                    arrayOf(strings[R.string.likes], strings[R.string.dislikes])
                ) { _, which ->
                    when (which) {
                        0 -> {
                            searchAdapter.getItems().sortedByDescending { it.likesCount.toInt() }
                                .let { sortedItems ->
                                    searchAdapter.update(sortedItems)
                                }
                        }
                        1 -> {
                            searchAdapter.getItems().sortedByDescending { it.dislikesCount.toInt() }
                                .let { sortedItems ->
                                    searchAdapter.update(sortedItems)
                                }
                        }
                        else -> Unit
                    }

                }
            }
            dialogBuilder.create().show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
