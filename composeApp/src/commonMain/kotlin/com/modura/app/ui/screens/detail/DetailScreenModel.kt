package com.modura.app.ui.screens.detail

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.domain.model.response.youtube.YoutubeModel
import com.modura.app.domain.repository.YoutubeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class DetailUiState(
    val videos: List<YoutubeModel> = emptyList(),
    val isYoutubeLoading: Boolean = false,
    val youtubeErrorMessage: String? = null
)

class DetailScreenModel(
    private val repository: YoutubeRepository
) : ScreenModel, KoinComponent {
    
    val uiState = mutableStateOf(DetailUiState())

    fun getYoutubeVideos(query: String) {
        screenModelScope.launch {
            uiState.value = uiState.value.copy(isYoutubeLoading = true, youtubeErrorMessage = null)
            repository.getYoutubeVideos(query)
                .onSuccess {
                    uiState.value = uiState.value.copy(videos = it, isYoutubeLoading = false)
                }.onFailure {
                    it.printStackTrace()
                    uiState.value = uiState.value.copy(isYoutubeLoading = false, youtubeErrorMessage = "영상을 불러오는 중 오류가 발생했습니다.")
                }
        }

    }


}

