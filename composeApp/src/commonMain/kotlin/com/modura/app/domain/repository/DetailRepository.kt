package com.modura.app.domain.repository

import com.modura.app.domain.model.request.detail.ContentReviewRequestModel
import com.modura.app.domain.model.response.detail.ContentDetailResponseModel
import com.modura.app.domain.model.response.detail.ContentReviewResponseModel
import com.modura.app.domain.model.response.detail.ContentReviewsResponseModel
import com.modura.app.domain.model.response.youtube.YoutubeModel

interface DetailRepository {
    suspend fun getYoutubeVideos(query: String): Result<List<YoutubeModel>>
    suspend fun detailContent(contentId: Int): Result<ContentDetailResponseModel>
    suspend fun contentLike(contentId: Int): Result<Unit>
    suspend fun placeLike(placeId: Int): Result<Unit>
    suspend fun contentLikeCancel(contentId: Int): Result<Unit>
    suspend fun placeLikeCancel(placeId: Int): Result<Unit>
    suspend fun contentReviews(contentId: Int): Result<ContentReviewsResponseModel>
    suspend fun contentReview(contentId: Int, reviewId: Int): Result<ContentReviewResponseModel>
    suspend fun contentReviewRegister(contentId: Int, request: ContentReviewRequestModel): Result<Unit>
    suspend fun contentReviewEdit(contentId: Int, reviewId: Int, request: ContentReviewRequestModel): Result<Unit>
    suspend fun contentReviewDelete(contentId: Int, reviewId: Int): Result<Unit>
}