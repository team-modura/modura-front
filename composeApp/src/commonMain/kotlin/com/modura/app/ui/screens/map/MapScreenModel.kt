package com.modura.app.ui.screens.map

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.domain.Location
import com.modura.app.domain.model.response.map.PlaceResponseModel
import com.modura.app.domain.repository.MapRepository
import com.modura.app.util.location.LocationHelper
import com.modura.app.util.location.calculateDistance
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MapUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val places: List<PlaceResponseModel> = emptyList(),
    val currentLocation: Location? = null,
    val focusedPlace: PlaceResponseModel? = null,
    val cameraEvent: MapScreenModel.CameraEvent? = null,
    val errorMessage: String? = null
)

class MapScreenModel(
    private val repository: MapRepository,
    private val locationHelper: LocationHelper
) : ScreenModel {
    private val _scrollToTopEvent = MutableSharedFlow<Unit>()
    val scrollToTopEvent = _scrollToTopEvent.asSharedFlow()

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    private val _cameraEvent = MutableStateFlow<CameraEvent?>(null)
    val cameraEvent: StateFlow<CameraEvent?> = _cameraEvent.asStateFlow()

    private val _focusedPlaceId = MutableStateFlow<Int?>(null)
    val focusedPlaceId = _focusedPlaceId.asStateFlow()

    init {
        getPlacesByDistance()
    }
    fun getPlaces(query: String?) {
        screenModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = null) }
            repository.getPlaces(query).onSuccess { response -> _uiState.update { it.copy(inProgress = false, success = true, places = response.placeList) }
                _scrollToTopEvent.emit(Unit)
            }.onFailure { exception -> _uiState.update { it.copy(inProgress = false, success = false, errorMessage = exception.message) }
            }
        }
    }

    fun getPlacesByPopularity() {
        screenModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = null) }

            repository.getPlaces(null).onSuccess { response ->
                val sortedList = response.placeList.sortedByDescending { it.reviewCount }

                _uiState.update {
                    it.copy(
                        inProgress = false,
                        success = true,
                        places = sortedList
                    )
                }
                _scrollToTopEvent.emit(Unit)
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        inProgress = false,
                        success = false,
                        errorMessage = exception.message
                    )
                }
            }
        }
    }


    fun getPlacesByDistance() {
        screenModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = null) }

            val fetchedLocation = locationHelper.getCurrentLocation()
            if (fetchedLocation != null) {
                _uiState.update { it.copy(currentLocation = fetchedLocation) }
                println("### MapScreenModel: 현재 위치 획득 -> ${fetchedLocation.latitude}, ${fetchedLocation.longitude}")
                repository.getPlaces(null).onSuccess { response ->
                    val sortedList = response.placeList.sortedBy { place ->
                        val distance = calculateDistance(
                            fetchedLocation.latitude,
                            fetchedLocation.longitude,
                            place.latitude,
                            place.longitude
                        )
                        distance
                    }
                    _uiState.update { it.copy(inProgress = false, success = true, places = sortedList, currentLocation = fetchedLocation ) }
                    _scrollToTopEvent.emit(Unit)
                }.onFailure { exception ->
                    _uiState.update { it.copy(inProgress = false, success = false, errorMessage = exception.message) }
                }
            } else {
                _uiState.update { it.copy(inProgress = false, success = false, errorMessage = "현재 위치를 가져올 수 없습니다. 권한을 확인해주세요.") }
            }
        }
    }

    fun moveToCurrentLocation() {
        _uiState.value.currentLocation?.let {
            _cameraEvent.value = CameraEvent.MoveTo(it.longitude, it.latitude)
            _uiState.update { state -> state.copy(cameraEvent =  CameraEvent.MoveTo(it.longitude, it.latitude)) }
        }
    }
    fun setFocusedPlace(place: PlaceResponseModel) {
        if (_focusedPlaceId.value == place.id) return

        screenModelScope.launch {
            _focusedPlaceId.value = place.id

            _uiState.update {
                it.copy(
                    cameraEvent = CameraEvent.MoveTo(
                        lat = place.longitude,
                        lon = place.latitude
                    )
                )
            }
        }
    }
    fun consumeCameraEvent() {
        _cameraEvent.value = null
        _uiState.update { state -> state.copy(cameraEvent = null) }
    }
    sealed class CameraEvent {
        data class MoveTo(val lon: Double, val lat: Double) : CameraEvent()
    }
}