package com.modura.app.data.dto.request.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val code: String
)