package com.modura.app.domain.repository

import com.modura.app.domain.model.response.ai.RecommendationResponseModel
import com.modura.app.domain.model.response.ai.RecommendationsResponseModel
import com.modura.app.domain.model.response.map.PlacesResponseModel

interface AiRepository {
    suspend fun recommendation(userId: Long): Result<RecommendationsResponseModel>
    suspend fun aiPlaces(userId: Int, lat: Float, lon: Float): Result<PlacesResponseModel>
}