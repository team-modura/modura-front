package com.modura.app.data.dto.response.detail

import com.modura.app.domain.model.response.detail.StillcutResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class StillcutResponseDto(
    val stillcutId: Int,
    val contentId: Int,
    val title: String,
    val imageUrl: String
){
    fun toStillcutResponseModel() = StillcutResponseModel(stillcutId, contentId, title, imageUrl)
}
