package com.modura.app.ui.screens.map

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.domain.Location
import com.modura.app.domain.model.response.map.PlaceResponseModel
import com.modura.app.domain.repository.MapRepository
import com.modura.app.util.location.LocationHelper
import com.modura.app.util.location.calculateDistance
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MapUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val places: List<PlaceResponseModel> = emptyList(),
    val currentLocation: Location? = null,
    val focusedPlace: PlaceResponseModel? = null,
    val errorMessage: String? = null
)

class MapScreenModel(
    private val repository: MapRepository,
    private val locationHelper: LocationHelper
) : ScreenModel {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        getPlacesByDistance()
    }
    fun getPlaces(query: String?) {
        screenModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = null) }
            repository.getPlaces(query).onSuccess { response -> _uiState.update { it.copy(inProgress = false, success = true, places = response.placeList) }
            }.onFailure { exception -> _uiState.update { it.copy(inProgress = false, success = false, errorMessage = exception.message) }
            }
        }
    }

    fun getPlacesByDistance() {
        screenModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = null) }

            val currentLocation = locationHelper.getCurrentLocation()

            if (currentLocation != null) {
                println("### MapScreenModel: 현재 위치 획득 -> ${currentLocation.latitude}, ${currentLocation.longitude}")
                repository.getPlaces(null).onSuccess { response ->
                    val sortedList = response.placeList.sortedBy { place ->
                        val distance = calculateDistance(
                            currentLocation.latitude,
                            currentLocation.longitude,
                            place.latitude,
                            place.longitude
                        )
                        distance
                    }
                    _uiState.update { it.copy(inProgress = false, success = true, places = sortedList, currentLocation = currentLocation ) }
                }.onFailure { exception ->
                    _uiState.update { it.copy(inProgress = false, success = false, errorMessage = exception.message) }
                }
            } else {
                _uiState.update { it.copy(inProgress = false, success = false, errorMessage = "현재 위치를 가져올 수 없습니다. 권한을 확인해주세요.") }
            }
        }
    }
    fun setFocusedPlace(place: PlaceResponseModel?) {
        _uiState.update {
            it.copy(
                focusedPlace = place,
                currentLocation = if (place != null) Location(place.latitude, place.longitude) else it.currentLocation
            )
        }
    }
}