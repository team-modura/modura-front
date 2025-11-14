package com.modura.app.data.datasourceImpl

import com.modura.app.data.datasource.DetailDataSource
import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.request.detail.ContentReviewRequestDto
import com.modura.app.data.dto.request.detail.PlaceReviewRequestDto
import com.modura.app.data.dto.request.detail.StillcutRequestDto
import com.modura.app.data.dto.request.detail.UploadImageRequestDto
import com.modura.app.data.dto.response.detail.*
import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto
import com.modura.app.data.service.YoutubeService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

class DetailDataSourceImpl(
    private val httpClient: HttpClient,
    private val service: YoutubeService
) : DetailDataSource {
    override suspend fun searchYoutubeVideos(query: String): YoutubeSearchResponseDto = service.searchVideos(query)
    override suspend fun detailContent(contentId: Int): BaseResponse<ContentDetailResponseDto> = httpClient.get("/contents/$contentId/detail").body()
    override suspend fun detailPlace(placeId: Int): BaseResponse<PlaceDetailResponseDto> =httpClient.get("/places/$placeId/detail").body()
    override suspend fun contentLike(contentId: Int): BaseResponse<Unit> = httpClient.post("/contents/$contentId/like").body()
    override suspend fun placeLike(placeId: Int): BaseResponse<Unit> = httpClient.post("/places/$placeId/like").body()
    override suspend fun contentLikeCancel(contentId: Int): BaseResponse<Unit> = httpClient.delete("/contents/$contentId/like").body()
    override suspend fun placeLikeCancel(placeId: Int): BaseResponse<Unit> = httpClient.delete("/places/$placeId/like").body()
    override suspend fun contentReviews(contentId: Int): BaseResponse<ContentReviewsResponseDto> = httpClient.get("/contents/$contentId/reviews").body()
    override suspend fun placeReviews(placeId: Int): BaseResponse<PlaceReviewsResponseDto> = httpClient.get("/places/$placeId/reviews").body()
    override suspend fun contentReview(contentId: Int, reviewId: Int): BaseResponse<ContentReviewResponseDto> = httpClient.get("/contents/$contentId/reviews/$reviewId").body()
    override suspend fun contentReviewRegister(contentId: Int, request: ContentReviewRequestDto): BaseResponse<Unit> = httpClient.post("/contents/$contentId/reviews"){setBody(request)}.body()
    override suspend fun placeReviewRegister(placeId: Int, request: PlaceReviewRequestDto): BaseResponse<Unit> = httpClient.post("/places/$placeId/reviews"){setBody(request)}.body()
    override suspend fun contentReviewEdit(contentId: Int, reviewId: Int, request: ContentReviewRequestDto): BaseResponse<Unit> = httpClient.patch("/contents/$contentId/reviews/$reviewId"){setBody(request)}.body()
    override suspend fun contentReviewDelete(contentId: Int, reviewId: Int): BaseResponse<Unit> = httpClient.delete("/contents/$contentId/reviews/$reviewId").body()
    override suspend fun stillcut(placeId: Int): BaseResponse<StillcutResponseDto> = httpClient.get("/places/$placeId/stillcuts").body()
    override suspend fun stillcutSave(placeId: Int, stullcutId: Int, request: StillcutRequestDto): BaseResponse<Unit> = httpClient.post("/places/$placeId/stillcuts/$stullcutId"){setBody(request)}.body()
    override suspend fun uploadImage(request: UploadImageRequestDto): BaseResponse<List<UploadImageResponseDto>> =  httpClient.post("/s3/presigned-upload"){setBody(request)}.body()
}