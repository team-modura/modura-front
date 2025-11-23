package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.AiDataSource
import com.modura.app.domain.model.response.ai.RecommendationResponseModel
import com.modura.app.domain.model.response.ai.RecommendationsResponseModel
import com.modura.app.domain.model.response.map.PlacesResponseModel
import com.modura.app.domain.repository.AiRepository

class AiRepositoryImpl(
    private val  dataSource: AiDataSource
) : AiRepository {

    override suspend fun recommendation(userId: Long): Result<RecommendationsResponseModel> =
        runCatching { dataSource.recommendation(userId).toRecommendationsResponseModel() }

    override suspend fun aiPlaces(userId: Int, lat: Float, lon: Float): Result<PlacesResponseModel> =
        runCatching { dataSource.aiPlaces(userId, lat, lon).toPlacesResponseModel() }

}