package com.modura.app.domain.repository

import com.modura.app.domain.model.request.detail.ContentReviewRequestModel
import com.modura.app.domain.model.request.detail.PlaceReviewRequestModel
import com.modura.app.domain.model.request.detail.StillcutRequestModel
import com.modura.app.domain.model.request.detail.UploadImageRequestModel
import com.modura.app.domain.model.response.detail.*
import com.modura.app.domain.model.response.youtube.YoutubeModel

interface DetailRepository {
    suspend fun getYoutubeVideos(query: String): Result<List<YoutubeModel>>
    suspend fun detailContent(contentId: Int): Result<ContentDetailResponseModel>
    suspend fun detailPlace(placeId: Int): Result<PlaceDetailResponseModel>
    suspend fun contentLike(contentId: Int): Result<Unit>
    suspend fun placeLike(placeId: Int): Result<Unit>
    suspend fun contentLikeCancel(contentId: Int): Result<Unit>
    suspend fun placeLikeCancel(placeId: Int): Result<Unit>
    suspend fun contentReviews(contentId: Int): Result<ContentReviewsResponseModel>
    suspend fun placeReviews(placeId: Int): Result<PlaceReviewsResponseModel>
    suspend fun contentReview(contentId: Int, reviewId: Int): Result<ContentReviewResponseModel>
    suspend fun contentReviewRegister(contentId: Int, request: ContentReviewRequestModel): Result<Unit>
    suspend fun placeReviewRegister(placeId: Int, request: PlaceReviewRequestModel): Result<Unit>
    suspend fun contentReviewEdit(contentId: Int, reviewId: Int, request: ContentReviewRequestModel): Result<Unit>
    suspend fun contentReviewDelete(contentId: Int, reviewId: Int): Result<Unit>
    suspend fun stillcut(placeId: Int): Result<StillcutsResponseModel>
    suspend fun stillcutSave(placeId: Int, stillcutId: Int, request: StillcutRequestModel): Result<Unit>
    suspend fun uploadImage(request: UploadImageRequestModel): Result<List<UploadImageResponseModel>>
}