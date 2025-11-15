package com.modura.app.ui.screens.map

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.domain.model.response.map.PlaceResponseModel
import com.modura.app.domain.repository.MapRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MapUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val places: List<PlaceResponseModel> = emptyList(),
    val errorMessage: String? = null
)

class MapScreenModel(
    private val repository: MapRepository,
) : ScreenModel {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    fun getPlaces(query: String?) {
        screenModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = null) }
            repository.getPlaces(query).onSuccess {response->
                _uiState.update { it.copy(inProgress = false,success = true, places = response.placeList) }
            }.onFailure {
                _uiState.update { it.copy(  inProgress = false, success = false, errorMessage = it.errorMessage) }
            }
        }
    }


}