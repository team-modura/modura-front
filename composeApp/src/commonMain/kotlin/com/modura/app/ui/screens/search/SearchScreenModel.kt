package com.modura.app.ui.screens.search

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.domain.model.response.search.SearchContentResponseModel
import com.modura.app.domain.model.response.search.SearchPlaceResponseModel
import com.modura.app.domain.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchUiState(
    val inProgress: Boolean = false,
    val contents: List<SearchContentResponseModel> = emptyList(),
    val places: List<SearchPlaceResponseModel> = emptyList(),
    val errorMessage: String? = null
)


class SearchScreenModel(
    private val repository: SearchRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    fun searchContents(query: String) {
        screenModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = null) }
            repository.searchContents(query).onSuccess {
                val contents = it.contentList
                if (contents.isNotEmpty()) { _uiState.update { uiState -> uiState.copy(inProgress = false, contents = contents) }
                } else { _uiState.update { it.copy(inProgress = false, contents = emptyList(),errorMessage = "검색 결과가 없습니다.") } }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun searchPlaces(query: String){
        screenModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = null) }
            repository.searchPlaces(query).onSuccess {
                val places = it.placeList
                if (places.isNotEmpty()) { _uiState.update { uiState -> uiState.copy(inProgress = false, places = places) }
                } else { _uiState.update { it.copy(inProgress = false, places = emptyList(),errorMessage = "검색 결과가 없습니다.") } }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}
