package com.ali.android.urbandictionary.features

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ali.android.urbandictionary.base.DictionaryDispatchers
import com.ali.android.urbandictionary.extension.ContextProviderViewModel
import com.ali.android.urbandictionary.features.model.ViewState
import com.ali.android.urbandictionary.remote.model.ApiResult
import com.ali.android.urbandictionary.repository.DictionaryDataSource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val dataSource: DictionaryDataSource,
    private val dispatchers: DictionaryDispatchers,
    application: Application
) : ContextProviderViewModel(application) {

    val definitionsLiveData = MutableLiveData<ViewState>()

    fun getDefinitions(term: String) {
        launch(dispatchers.ui) {
            getDefinitionsForTerm(term)
        }
    }

    private suspend fun getDefinitionsForTerm(term: String) =
        when (val apiResult =
            withContext(dispatchers.io) { dataSource.getDefinitions(term) }) {
            is ApiResult.Success -> {
                if (apiResult.response.result.isNotEmpty()) {
                    definitionsLiveData.postValue(ViewState.GetDefinitionsItemsSuccess(apiResult.response.result))
                } else {
                    definitionsLiveData.postValue(ViewState.GetDefinitionItemsEmpty)
                }
            }
            is ApiResult.Failure -> {
                definitionsLiveData.postValue(ViewState.GetDefinitionItemsFailed)
            }
            ApiResult.GenericError -> {
                definitionsLiveData.postValue(ViewState.GetDefinitionItemsFailed)
            }
        }
}
