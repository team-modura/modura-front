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
import com.modura.app.domain.repository.TokenRepository
import com.modura.app.ui.screens.login.LoginScreen
import com.modura.app.ui.screens.login.LoginScreenModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.remove

data class MypageUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null
)
data class MypageReview(
    val id: Int,
    val contentId: Int?,
    val placeId: Int?,
    val type: String, // "series", "movies", "장소"
    val title: String,
    val username: String,
    val rating: Int,
    val comment: String,
    val createdAt: String,
    val thumbnail: String?,
    val imageUrl: List<String> = emptyList()
)

class MypageScreenModel(
    private val repository: MypageRepository,
    private  val tokenRepository: TokenRepository,
    private val loginScreenModel: LoginScreenModel
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

    private val _reviews = MutableStateFlow<List<MypageReview>>(emptyList())
    val reviews = _reviews.asStateFlow()

    fun getLikedContents(type: String) {
        screenModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = null) }
            repository.contentsLikes(type)
                .onSuccess { responseModel ->
                    when (type) {
                        "series" -> _likedSeries.value = responseModel.contentList
                        "movie" -> _likedMovies.value = responseModel.contentList
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

    fun getContentReviewsMypage(type: String){
        screenModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = null) }
            repository.contentReviewsMypage(type).onSuccess {
                val newReiviews = it.contentReviewList.map{
                    MypageReview(
                        id = it.id,
                        contentId = it.contentId,
                        placeId = null,
                        type = type,
                        title = it.title,
                        username = it.username,
                        rating = it.rating,
                        comment = it.comment,
                        createdAt = it.createdAt,
                        thumbnail = it.thumbnail,
                        imageUrl = emptyList()
                    )
                }
                _reviews.value = newReiviews
                _uiState.update { it.copy(inProgress = false, success = true) }
            }.onFailure {
                _uiState.update { it.copy(inProgress = false, errorMessage = "목록을 불러오지 못했습니다.")}
            }
        }
    }

    fun getPlaceReviewsMypage(type: String){
        screenModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = null) }
            repository.placeReviewsMypage(type).onSuccess {
                val newReiviews = it.placeReviewList.map{
                    MypageReview(
                        id = it.id,
                        contentId = null,
                        placeId = it.placeId,
                        type = type,
                        title = it.name,
                        username = it.username,
                        rating = it.rating,
                        comment = it.comment,
                        createdAt = it.createdAt,
                        thumbnail = it.thumbnail,
                        imageUrl = it.imageUrl
                    )
                }
                _reviews.value = newReiviews
                _uiState.update { it.copy(inProgress = false, success = true) }
            }.onFailure {
                _uiState.update { it.copy(inProgress = false, errorMessage = "목록을 불러오지 못했습니다.")}
            }
        }
    }

    fun logout() {
        screenModelScope.launch {
            repository.logout()
            println("로그아웃 성공")
            tokenRepository.clearTokens()
            println("토큰 삭제 완료.")
            loginScreenModel.resetLoginState()
            println("로그아웃 절차 완료: LoginScreen으로 이동을 요청합니다.")
        }
    }

    suspend fun withdraw(): Boolean { // 함수를 suspend로 변경
        return try {
            repository.withdraw()
            tokenRepository.clearTokens()
            println("회원탈퇴 성공")
            true
        } catch (e: Exception) {
            println("회원탈퇴 실패: ${e.message}")
            false
        }
    }
    fun clearReviews() {
        _reviews.value = emptyList()
    }
}