package com.modura.app.domain.model.request.detail

import com.modura.app.data.dto.request.detail.StillcutRequestDto
import com.modura.app.domain.model.response.detail.StillcutResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class StillcutRequestModel(
    val imageUrl: String,
    val similarity: Int,
    val angle: Int,
    val clarity: Int,
    val color: Int,
    val palette: Int
){
    fun toStillcutRequestDto() =
        StillcutRequestDto(imageUrl, similarity, angle, clarity, color, palette)
}