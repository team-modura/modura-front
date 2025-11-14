package com.modura.app.domain.model.response.mypage

import com.modura.app.data.dto.request.detail.StillcutRequestDto
import com.modura.app.domain.model.response.detail.StillcutResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class StillcutDetailResponseModel(
    val id: Int,
    val imageUrl: String,
    val stillcut: String,
    val title: String,
    val name: String,
    val date: String
)