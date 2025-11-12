package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.DetailDataSource
import com.modura.app.domain.model.request.detail.ContentReviewRequestModel
import com.modura.app.domain.model.request.detail.PlaceReviewRequestModel
import com.modura.app.domain.model.request.detail.StillcutRequestModel
import com.modura.app.domain.model.response.detail.*
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
            if (response.isSuccess && response.result != null) { response.result.toContentDetailResponseModel()
            } else { throw Exception(response.message ?: "상세 정보를 불러오는데 실패했습니다.") }
        }

    override suspend fun contentLike(contentId: Int): Result<Unit> =
        runCatching { dataSource.detailContent(contentId) }

    override suspend fun placeLike(placeId: Int): Result<Unit> =
        runCatching { dataSource.detailContent(placeId) }

    override suspend fun contentLikeCancel(contentId: Int): Result<Unit> =
        runCatching { dataSource.detailContent(contentId)  }

    override suspend fun placeLikeCancel(placeId: Int): Result<Unit> =
        runCatching { dataSource.detailContent(placeId)  }

    override suspend fun contentReviews(contentId: Int): Result<ContentReviewsResponseModel> =
        runCatching { dataSource.contentReviews(contentId).result!!.toContentReviewsResponseModel() }

    override suspend fun placeReviews(placeId: Int): Result<PlaceReviewsResponseModel> =
        runCatching { dataSource.placeReviews(placeId).result!!.toPlaceReviewsResponseModel() }

    override suspend fun contentReview(contentId: Int, reviewId: Int): Result<ContentReviewResponseModel> =
        runCatching { dataSource.contentReview(contentId, reviewId).result!!.toContentReviewResponseModel() }

    override suspend fun contentReviewRegister(contentId: Int, request: ContentReviewRequestModel): Result<Unit> =
        runCatching { dataSource.contentReviewRegister(contentId, request.toContentReviewRequestDto()) }

    override suspend fun placeReviewRegister(placeId: Int, request: PlaceReviewRequestModel): Result<Unit> =
        runCatching { dataSource.placeReviewRegister(placeId, request.toPlaceReviewRequestDto()) }

    override suspend fun contentReviewEdit(contentId: Int, reviewId: Int, request: ContentReviewRequestModel): Result<Unit> =
        runCatching { dataSource.contentReviewEdit(contentId, reviewId, request.toContentReviewRequestDto()) }

    override suspend fun contentReviewDelete(contentId: Int, reviewId: Int): Result<Unit> =
        runCatching { dataSource.contentReviewDelete(contentId, reviewId)}

    override suspend fun stillcut(placeId: Int): Result<StillcutResponseModel> =
        runCatching { dataSource.stillcut(placeId).result!!.toStillcutResponseModel() }

    override suspend fun stillcutSave(placeId: Int, stillcutId: Int, request: StillcutRequestModel): Result<Unit> =
        runCatching { dataSource.stillcutSave(placeId, stillcutId, request.toStillcutRequestDto()) }

}