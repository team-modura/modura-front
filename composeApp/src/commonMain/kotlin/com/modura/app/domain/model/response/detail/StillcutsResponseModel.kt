package com.modura.app.domain.model.response.detail

import com.modura.app.data.dto.response.detail.StillcutResponseDto
import com.modura.app.domain.model.response.detail.StillcutResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class StillcutsResponseModel(
    val stillcutList: List<StillcutResponseModel>
)