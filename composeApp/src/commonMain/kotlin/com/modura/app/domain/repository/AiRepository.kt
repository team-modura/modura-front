package com.modura.app.domain.repository

import com.modura.app.domain.model.response.ai.RecommendationResponseModel

interface AiRepository {
    suspend fun recommendation(userId: Long): Result<RecommendationResponseModel>
}