package com.modura.app.ui.screens.mypage

import androidx.compose.animation.core.copy
import androidx.compose.ui.semantics.getOrNull
import androidx.lifecycle.coroutineScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.domain.model.request.login.LoginRequestModel
import com.modura.app.domain.model.request.login.UserRequestModel
import com.modura.app.domain.model.response.mypage.ContentLikedResponseModel
import com.modura.app.domain.model.response.mypage.ContentsLikedResponseModel
import com.modura.app.domain.model.response.mypage.PlaceLikedResponseModel
import com.modura.app.domain.model.response.mypage.StillcutResponseModel
import com.modura.app.domain.model.response.mypage.StillcutsResponseModel
import com.modura.app.domain.repository.LoginRepository
import com.modura.app.domain.repository.MypageRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MypageUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null
)

class MypageScreenModel(
    private val repository: MypageRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow(MypageUiState())
    val uiState = _uiState.asStateFlow()


    private val _likedSeries = MutableStateFlow<List<ContentLikedResponseModel>>(emptyList())
    val likedSeries: StateFlow<List<ContentLikedResponseModel>> = _likedSeries.asStateFlow()

    private val _likedMovies = MutableStateFlow<List<ContentLikedResponseModel>>(emptyList())
    val likedMovies: StateFlow<List<ContentLikedResponseModel>> = _likedMovies.asStateFlow()

    private val _likedPlaces = MutableStateFlow<List<PlaceLikedResponseModel>>(emptyList())
    val likedPlaces: StateFlow<List<PlaceLikedResponseModel>> = _likedPlaces.asStateFlow()

    private val _stillcuts = MutableStateFlow<List<StillcutResponseModel>>(emptyList())
    val stillcuts: StateFlow<List<StillcutResponseModel>> = _stillcuts.asStateFlow()

    fun getLikedContents(type: String) {
        screenModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = null) }
            repository.contentsLikes(type)
                .onSuccess { responseModel ->
                    when (type) {
                        "series" -> _likedSeries.value = responseModel.contentList
                        "movies" -> _likedMovies.value = responseModel.contentList
                    }
                    _uiState.update { it.copy(inProgress = false, success = true) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(inProgress = false, errorMessage = "목록을 불러오지 못했습니다.") }
                    error.printStackTrace()
                }
        }
    }

    fun getLikedPlaces(){
        screenModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = null) }
            repository.placesLikes().onSuccess {
                _likedPlaces.value = it.placeList
                _uiState.update { it.copy(inProgress = false, success = true) }
            }.onFailure {
                _uiState.update { it.copy(inProgress = false, errorMessage = "목록을 불러오지 못했습니다.")}
                it.printStackTrace()
            }
        }
    }

    fun getStillcuts(){
        screenModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = null) }
            repository.stillcuts().onSuccess {
                _stillcuts.value = it.stillcutList
                _uiState.update { it.copy(inProgress = false, success = true) }
            }.onFailure {
                _uiState.update { it.copy(inProgress = false, errorMessage = "목록을 불러오지 못했습니다.")}
                it.printStackTrace()
            }
        }
    }

}