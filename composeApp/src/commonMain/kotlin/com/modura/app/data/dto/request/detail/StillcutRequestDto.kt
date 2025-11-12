package com.modura.app.data.dto.request.detail

import com.modura.app.domain.model.response.detail.StillcutResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class StillcutRequestDto(
    val imageUrl: String,
    val similarity: Int,
    val angle: Int,
    val clarity: Int,
    val color: Int,
    val palette: Int
)