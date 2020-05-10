package com.ali.android.urbandictionary.features.model

import com.ali.android.urbandictionary.remote.model.DefinitionResult

sealed class ViewState {
    data class GetDefinitionsItemsSuccess(val definitionResult: List<DefinitionResult>) : ViewState()
    object GetDefinitionItemsEmpty : ViewState()
    object GetDefinitionItemsFailed : ViewState()
}
