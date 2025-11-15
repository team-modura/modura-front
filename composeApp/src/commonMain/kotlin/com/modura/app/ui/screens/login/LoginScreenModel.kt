package com.modura.app.ui.screens.login

import androidx.compose.animation.core.copy
import androidx.compose.ui.semantics.getOrNull
import androidx.lifecycle.coroutineScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.domain.model.request.login.LoginRequestModel
import com.modura.app.domain.model.request.login.UserRequestModel
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

    fun resetLoginState() {
        _uiState.value = LoginUiState(inProgress = false, success = false)
        _isNewUser.value = false
        println("LoginScreenModel: 상태가 초기화되었습니다.")
    }
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _isNewUser = MutableStateFlow(false)
    val isNewUser = _isNewUser.asStateFlow()

    private val _userRegistrationSuccess = MutableStateFlow(false)
    val userRegistrationSuccess = _userRegistrationSuccess.asStateFlow()

    fun login(authCode: String) {
        if (_uiState.value.inProgress) return
        _uiState.update { it.copy(inProgress = true, errorMessage = null) }
        screenModelScope.launch {
            repository.login(LoginRequestModel(authCode)).onSuccess {
                println("MODURA 서버 로그인 성공: ${it.accessToken}")
                _isNewUser.value = it.isNewUser == true
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

    fun user(address: String, categoryList: List<Int>){
        screenModelScope.launch {
            val request = UserRequestModel(address = address, categoryList = categoryList)
            repository.user(request).onSuccess {
                println("사용자 정보 등록 성공")
                _uiState.update { it.copy(inProgress = false, success = true) }
            }.onFailure {error->
                _uiState.update { it.copy(inProgress = false, success = false, errorMessage = error.message ?: "알 수 없는 오류가 발생했습니다.") }
            }
        }
    }
}