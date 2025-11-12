package com.modura.app.ui.screens.detail

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.domain.model.response.youtube.YoutubeModel
import com.modura.app.domain.repository.DetailRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

data class YoutubeUiState(
    val videos: List<YoutubeModel> = emptyList(),
    val isYoutubeLoading: Boolean = false,
    val youtubeErrorMessage: String? = null
)

data class DetailUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null
)

class DetailScreenModel(
    private val repository: DetailRepository
) : ScreenModel, KoinComponent {
    
    val youtubeUiState = mutableStateOf(YoutubeUiState())

    fun getYoutubeVideos(query: String) {
        screenModelScope.launch {
            youtubeUiState.value = youtubeUiState.value.copy(isYoutubeLoading = true, youtubeErrorMessage = null)
            repository.getYoutubeVideos(query)
                .onSuccess {
                    youtubeUiState.value = youtubeUiState.value.copy(videos = it, isYoutubeLoading = false)
                }.onFailure {
                    it.printStackTrace()
                    youtubeUiState.value = youtubeUiState.value.copy(isYoutubeLoading = false, youtubeErrorMessage = "영상을 불러오는 중 오류가 발생했습니다.")
                }
        }
    }

    fun detailContent(contentId: Int){
        screenModelScope.launch {
            repository.detailContent(contentId).onSuccess {
                println(it)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun contentLike(contentId: Int){
        screenModelScope.launch {
            repository.contentLike(contentId).onSuccess {
                println(it)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun placeLike(placeId: Int) {
        screenModelScope.launch {
            repository.placeLike(placeId).onSuccess {
                println(it)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}

