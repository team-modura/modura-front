package com.modura.app.ui.screens.search

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.domain.repository.DetailRepository
import com.modura.app.domain.repository.SearchRepository
import com.modura.app.ui.screens.detail.YoutubeUiState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

data class SearchUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null
)


class SearchScreenModel(
    private val repository: SearchRepository
) : ScreenModel {

    val uiState = mutableStateOf(SearchUiState())

    fun searchContents(query: String) {
        screenModelScope.launch {
            uiState.value = uiState.value.copy(inProgress = true, errorMessage = null)
            repository.searchContents(query).onSuccess {
                uiState.value = uiState.value.copy(inProgress = false, success = true)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

}
