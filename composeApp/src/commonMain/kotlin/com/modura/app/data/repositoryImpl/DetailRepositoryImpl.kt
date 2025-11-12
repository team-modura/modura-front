package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.DetailDataSource
import com.modura.app.domain.model.response.detail.ContentDetailResponseModel
import com.modura.app.domain.model.response.youtube.YoutubeModel
import com.modura.app.domain.repository.DetailRepository

class DetailRepositoryImpl (
    private val dataSource: DetailDataSource
): DetailRepository {
    override suspend fun getYoutubeVideos(query: String): Result<List<YoutubeModel>> =
        runCatching { dataSource.searchYoutubeVideos(query).items.map { it.toYoutubeModel() } }

    override suspend fun detailContent(contentId: Int): Result<ContentDetailResponseModel> =
        runCatching {
            val response = dataSource.detailContent(contentId)
            if (response.isSuccess && response.result != null) {
                response.result.toContentDetailResponseModel()
            } else {
                throw Exception(response.message ?: "상세 정보를 불러오는데 실패했습니다.")
            }
        }

    override suspend fun contentLike(contentId: Int): Result<Unit> =
        runCatching { dataSource.detailContent(contentId) }

    override suspend fun placeLike(placeId: Int): Result<Unit> =
        runCatching { dataSource.detailContent(placeId) }

}