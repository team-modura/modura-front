package com.modura.app.domain.model.response.login

data class LoginResponseModel(
    val id: Int,
    val accessToken: String,
    val refreshToken: String,
    val isNewUser: Boolean,
    val username: String,
    val isInactive: Boolean
)
