package com.modura.app.ui.screens.ai

import com.modura.app.domain.repository.AiRepository
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.data.dto.response.ai.RecommendationResponseDto
import com.modura.app.domain.model.response.ai.RecommendationResponseModel
import com.modura.app.domain.model.response.search.SearchContentResponseModel
import com.modura.app.domain.repository.TokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AIUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null
)

class AIScreenModel(
    private val repository: AiRepository,
    private val tokenRepository: TokenRepository
) : ScreenModel {
    private val _userId = MutableStateFlow(0L)
    val userId: StateFlow<Long> = _userId

    private val _aiContents = MutableStateFlow<List<RecommendationResponseModel>>(emptyList())
    val aiContents = _aiContents.asStateFlow()

    init {
        screenModelScope.launch {
            _userId.value = tokenRepository.getUserId().toLong()
        }
    }

    fun getRecommendation(userId: Long) {
        screenModelScope.launch {
            repository.recommendation(userId).onSuccess {
                _aiContents.value = it.placeList
                println(it)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}