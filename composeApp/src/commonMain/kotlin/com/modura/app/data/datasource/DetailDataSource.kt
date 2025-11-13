package com.modura.app.data.datasource

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.request.detail.ContentReviewRequestDto
import com.modura.app.data.dto.request.detail.PlaceReviewRequestDto
import com.modura.app.data.dto.request.detail.StillcutRequestDto
import com.modura.app.data.dto.response.detail.*
import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto

interface DetailDataSource {
    suspend fun searchYoutubeVideos(query: String): YoutubeSearchResponseDto

    suspend fun detailContent(contentId: Int): BaseResponse<ContentDetailResponseDto>
    suspend fun contentLike(contentId: Int): BaseResponse<Unit>
    suspend fun placeLike(placeId: Int): BaseResponse<Unit>
    suspend fun contentLikeCancel(contentId: Int): BaseResponse<Unit>
    suspend fun placeLikeCancel(placeId: Int): BaseResponse<Unit>
    suspend fun contentReviews(contentId: Int): BaseResponse<ContentReviewsResponseDto>
    suspend fun placeReviews(placeId: Int): BaseResponse<PlaceReviewsResponseDto>
    suspend fun contentReview(contentId: Int, reviewId: Int): BaseResponse<ContentReviewResponseDto>
    suspend fun contentReviewRegister(contentId: Int, request: ContentReviewRequestDto): BaseResponse<Unit>
    suspend fun placeReviewRegister(placeId: Int, request: PlaceReviewRequestDto): BaseResponse<Unit>
    suspend fun contentReviewEdit(contentId: Int, reviewId: Int, request: ContentReviewRequestDto): BaseResponse<Unit>
    suspend fun contentReviewDelete(contentId: Int, reviewId: Int): BaseResponse<Unit>
    suspend fun stillcut(placeId: Int): BaseResponse<StillcutResponseDto>
    suspend fun stillcutSave(placeId: Int, stillcutId: Int, request: StillcutRequestDto): BaseResponse<Unit>
    suspend fun uploadImage(folder: String, fileName:List<String>, contentType:List<String>): BaseResponse<List<UploadImageResponseDto>>
}
