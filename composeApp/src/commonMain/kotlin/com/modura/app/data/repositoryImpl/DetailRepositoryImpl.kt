package com.modura.app.data.repositoryImpl

import com.modura.app.data.datasource.DetailDataSource
import com.modura.app.domain.model.request.detail.ContentReviewRequestModel
import com.modura.app.domain.model.request.detail.PlaceReviewRequestModel
import com.modura.app.domain.model.request.detail.StillcutRequestModel
import com.modura.app.domain.model.request.detail.UploadImageRequestModel
import com.modura.app.domain.model.response.detail.*
import com.modura.app.domain.model.response.youtube.YoutubeModel
import com.modura.app.domain.repository.DetailRepository

class DetailRepositoryImpl (
    private val dataSource: DetailDataSource
): DetailRepository {
    override suspend fun getYoutubeVideos(query: String): Result<List<YoutubeModel>> =
        runCatching { dataSource.searchYoutubeVideos(query).items.map { it.toYoutubeModel() } }

    override suspend fun detailContent(contentId: Int): Result<ContentDetailResponseModel> =
        runCatching { dataSource.detailContent(contentId).result!!.toContentDetailResponseModel() }

    override suspend fun detailPlace(placeId: Int): Result<PlaceDetailResponseModel> =
        runCatching { dataSource.detailPlace(placeId).result!!.toPlaceDetailResponseModel() }

    override suspend fun contentLike(contentId: Int): Result<Unit> =
        runCatching { dataSource.contentLike(contentId) }

    override suspend fun placeLike(placeId: Int): Result<Unit> =
        runCatching { dataSource.placeLike(placeId) }

    override suspend fun contentLikeCancel(contentId: Int): Result<Unit> =
        runCatching { dataSource.contentLikeCancel(contentId)  }

    override suspend fun placeLikeCancel(placeId: Int): Result<Unit> =
        runCatching { dataSource.placeLikeCancel(placeId)  }

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

    override suspend fun stillcut(placeId: Int): Result<StillcutsResponseModel> =
        runCatching { dataSource.stillcut(placeId).result!!.toStillcutsResponseModel() }

    override suspend fun stillcutSave(placeId: Int, stillcutId: Int, request: StillcutRequestModel): Result<Unit> =
        runCatching { dataSource.stillcutSave(placeId, stillcutId, request.toStillcutRequestDto()) }

    override suspend fun uploadImage(request: UploadImageRequestModel): Result<List<UploadImageResponseModel>>     {
            val response = dataSource.uploadImage(request.toUploadImageRequestDto())

            if (response.isSuccess && response.result != null) {
                val models = response.result.map { dto -> dto.toUploadImageResponseModel() }
                return Result.success(models)
            } else {
                  val errorMessage =
                    "Presigned URL 요청 실패: ${response.message} (Code: ${response.code})"
                return Result.failure(Exception(errorMessage))
            }
        }
}