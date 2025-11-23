package com.modura.app.data.datasource

import com.modura.app.data.dto.response.ai.RecommendationResponseDto
import com.modura.app.data.dto.response.ai.RecommendationsResponseDto

interface AiDataSource {
    suspend fun recommendation(userId:Long): RecommendationsResponseDto
}