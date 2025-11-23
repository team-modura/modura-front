package com.modura.app.data.dto.response.ai

import com.modura.app.domain.model.response.ai.RecommendationResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationResponseDto(
    val id: Int,
    val name: String,
    val isLiked: Boolean,
    val thumbnail: String?
){
    fun toRecommendationResponseModel() = RecommendationResponseModel(id, name, isLiked, thumbnail)
}
