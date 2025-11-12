package com.modura.app.data.dto.response.login

import com.modura.app.domain.model.response.login.LoginResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val id: Int,
    val accessToken: String,
    val refreshToken: String,
){
    fun toLoginResponseModel()= LoginResponseModel(id, accessToken, refreshToken)
}
