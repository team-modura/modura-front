package com.modura.app.domain.model.response.ai

import kotlinx.serialization.Serializable

@Serializable
data class RecommendationsResponseModel(
    val placeList: List<RecommendationResponseModel>
)