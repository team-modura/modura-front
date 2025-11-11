package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.DetailDataSource
import com.modura.app.domain.model.request.detail.DetailResponseModel
import com.modura.app.domain.model.response.youtube.YoutubeModel
import com.modura.app.domain.repository.DetailRepository

class DetailRepositoryImpl (
    private val dataSource: DetailDataSource
): DetailRepository{
    override suspend fun getYoutubeVideos(query: String): Result<List<YoutubeModel>> =
        runCatching { dataSource.searchYoutubeVideos(query).items.map { it.toYoutubeModel() } }

    override suspend fun detailContent(contentId: Int): Result<DetailResponseModel> =
        runCatching { dataSource.detailContent(contentId).toDetailResponseModel() }
}