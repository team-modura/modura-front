package com.modura.app.ui.screens.ai

import com.modura.app.domain.repository.AiRepository
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch

data class AIUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null
)

class AIScreenModel(
    private val repository: AiRepository
) : ScreenModel {
    
    fun getRecommendation(userId: Long) {
        screenModelScope.launch {
            repository.recommendation(userId).onSuccess {
                println(it)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}