package com.modura.app.domain.model.response.mypage

import kotlinx.serialization.Serializable

@Serializable
data class StillcutsResponseModel(
    val stillcutList: List<StillcutResponseModel>
)