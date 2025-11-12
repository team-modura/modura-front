package com.modura.app.data.datasource

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.request.detail.ContentReviewRequestDto
import com.modura.app.data.dto.response.detail.ContentDetailResponseDto
import com.modura.app.data.dto.response.detail.ContentReviewResponseDto
import com.modura.app.data.dto.response.detail.ContentReviewsResponseDto
import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto

interface DetailDataSource {
    suspend fun searchYoutubeVideos(query: String): YoutubeSearchResponseDto

    suspend fun detailContent(contentId: Int): BaseResponse<ContentDetailResponseDto>
    suspend fun contentLike(contentId: Int): BaseResponse<Unit>
    suspend fun placeLike(placeId: Int): BaseResponse<Unit>
    suspend fun contentLikeCancel(contentId: Int): BaseResponse<Unit>
    suspend fun placeLikeCancel(placeId: Int): BaseResponse<Unit>
    suspend fun contentReviews(contentId: Int): BaseResponse<ContentReviewsResponseDto>
    suspend fun contentReview(contentId: Int, reviewId: Int): BaseResponse<ContentReviewResponseDto>
    suspend fun contentReviewRegister(contentId: Int, request: ContentReviewRequestDto): BaseResponse<Unit>
    suspend fun contentReviewEdit(contentId: Int, reviewId: Int, request: ContentReviewRequestDto): BaseResponse<Unit>
    suspend fun contentReviewDelete(contentId: Int, reviewId: Int): BaseResponse<Unit>
}
