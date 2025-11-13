package com.modura.app.ui.screens.search

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.data.dto.response.search.SearchResponseDto
import com.modura.app.domain.model.response.search.SearchResponseModel
import com.modura.app.domain.repository.DetailRepository
import com.modura.app.domain.repository.SearchRepository
import com.modura.app.ui.screens.detail.YoutubeUiState
import com.modura.app.ui.screens.login.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

data class SearchUiState(
    val inProgress: Boolean = false,
    val contents: List<SearchResponseModel> = emptyList(),
    val places: List<SearchResponseModel> = emptyList(),
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
                if (it != null) { _uiState.update { it.copy(inProgress = false, places = it.contents) }
                } else { _uiState.update { it.copy(inProgress = false, errorMessage = "검색 결과가 없습니다.") } }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}
