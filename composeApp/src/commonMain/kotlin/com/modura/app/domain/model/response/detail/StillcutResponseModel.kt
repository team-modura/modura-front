package com.modura.app.domain.model.response.detail

import kotlinx.serialization.Serializable

@Serializable
data class StillcutResponseModel(
    val stillcutId: Int,
    val contentId: Int,
    val title: String,
    val imageUrl: String
)
