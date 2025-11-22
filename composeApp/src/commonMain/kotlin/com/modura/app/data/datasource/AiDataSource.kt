package com.modura.app.data.datasource

import com.modura.app.data.dto.response.ai.RecommendationResponseDto

interface AiDataSource {
    suspend fun recommendation(userId:Long): RecommendationResponseDto
}