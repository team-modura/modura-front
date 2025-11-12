package com.modura.app.ui.screens.login

import androidx.compose.animation.core.copy
import androidx.compose.ui.semantics.getOrNull
import androidx.lifecycle.coroutineScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.domain.model.request.login.LoginRequestModel
import com.modura.app.domain.repository.LoginRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null
)

class LoginScreenModel(
    private val repository: LoginRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun login(authCode: String) {
        if (_uiState.value.inProgress) return
        _uiState.update { it.copy(inProgress = true, errorMessage = null) }
        screenModelScope.launch {
            repository.login(LoginRequestModel(authCode)).onSuccess {
                println("MODURA 서버 로그인 성공: ${it.accessToken}")
                _uiState.update {
                    it.copy(
                        inProgress = false,
                        success = true
                    )
                }
            }.onFailure {error ->
                println("MODURA 서버 로그인 실패: ${error.message}")
                _uiState.update {
                    it.copy(
                        inProgress = false,
                        success = false,
                        errorMessage = error.message
                    )
                }
            }
        }
    }
}