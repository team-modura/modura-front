package com.modura.app.data.dto.response.ai

import com.modura.app.domain.model.response.ai.RecommendationResponseModel
import com.modura.app.domain.model.response.ai.RecommendationsResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationsResponseDto(
    val placeList : List<RecommendationResponseDto>
){
    fun toRecommendationsResponseModel() = RecommendationsResponseModel(placeList.map { it.toRecommendationResponseModel() })
}
