package com.modura.app.domain.model.response.ai

import kotlinx.serialization.Serializable

@Serializable
data class RecommendationResponseModel(
    val id: Int,
    val name: String,
    val isLiked: Boolean,
    val thumbnail: String?
)
