package com.modura.app.domain.model.request.login

import com.modura.app.data.dto.request.login.LoginRequestDto
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestModel (
    val code: String
) {
    fun  toLoginRequestDto() = LoginRequestDto(code)
}