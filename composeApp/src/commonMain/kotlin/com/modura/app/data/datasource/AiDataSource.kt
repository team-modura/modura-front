package com.modura.app.data.datasource

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.ai.RecommendationResponseDto
import com.modura.app.data.dto.response.ai.RecommendationsResponseDto
import com.modura.app.data.dto.response.map.PlacesResponseDto

interface AiDataSource {
    suspend fun recommendation(userId:Long): RecommendationsResponseDto
    suspend fun aiPlaces(userId: Int, lat: Float, lon: Float): PlacesResponseDto
}