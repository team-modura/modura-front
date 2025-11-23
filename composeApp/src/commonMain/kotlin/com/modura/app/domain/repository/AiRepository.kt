package com.modura.app.domain.repository

import com.modura.app.domain.model.response.ai.RecommendationResponseModel
import com.modura.app.domain.model.response.ai.RecommendationsResponseModel

interface AiRepository {
    suspend fun recommendation(userId: Long): Result<RecommendationsResponseModel>
}