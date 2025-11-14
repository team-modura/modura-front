package com.modura.app.data.dto.response.detail

import com.modura.app.domain.model.response.detail.StillcutResponseModel
import com.modura.app.domain.model.response.detail.StillcutsResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class StillcutsResponseDto(
    val stillcutList: List<StillcutResponseDto>
){
    fun toStillcutsResponseModel() = StillcutsResponseModel(stillcutList.map { it.toStillcutResponseModel() })
}
