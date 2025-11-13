package com.modura.app.ui.screens.mypage

import androidx.compose.animation.core.copy
import androidx.compose.ui.semantics.getOrNull
import androidx.lifecycle.coroutineScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.domain.model.request.login.LoginRequestModel
import com.modura.app.domain.model.request.login.UserRequestModel
import com.modura.app.domain.repository.LoginRepository
import com.modura.app.domain.repository.MypageRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
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



}