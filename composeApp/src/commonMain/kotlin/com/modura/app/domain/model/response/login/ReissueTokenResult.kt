package com.modura.app.domain.model.response.login

import kotlinx.serialization.Serializable

@Serializable
data class ReissueTokenResult(
    val id: Int,
    val accessToken: String,
    val refreshToken: String,
    val isNewUser: Boolean
)
