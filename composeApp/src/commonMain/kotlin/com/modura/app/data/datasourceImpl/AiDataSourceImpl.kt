package com.modura.app.data.datasourceImpl

import com.modura.app.data.datasource.AiDataSource
import com.modura.app.data.dto.response.ai.RecommendationResponseDto
import com.modura.app.data.dto.response.ai.RecommendationsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class AiDataSourceImpl(
    private val httpClient: HttpClient
): AiDataSource {
    override suspend fun recommendation(userId: Long): RecommendationsResponseDto = httpClient.get("/recommend/contents/${userId}").body()
}