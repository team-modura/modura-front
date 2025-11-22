package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.AiDataSource
import com.modura.app.domain.model.response.ai.RecommendationResponseModel
import com.modura.app.domain.repository.AiRepository

class AiRepositoryImpl(
    private val  dataSource: AiDataSource
) : AiRepository {

    override suspend fun recommendation(userId: Long): Result<RecommendationResponseModel> =
        runCatching { dataSource.recommendation(userId).toRecommendationResponseModel() }

}