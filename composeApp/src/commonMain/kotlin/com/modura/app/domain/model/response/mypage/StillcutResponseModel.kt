package com.modura.app.domain.model.response.mypage

import kotlinx.serialization.Serializable

@Serializable
data class StillcutResponseModel(
    val id: Int,
    val imageUrl: String
)