package com.modura.app.data.datasourceImpl

import com.modura.app.data.datasource.DetailDataSource
import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.detail.ContentDetailResponseDto
import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto
import com.modura.app.data.service.YoutubeService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post

class DetailDataSourceImpl(
    private val httpClient: HttpClient,
    private val service: YoutubeService
) : DetailDataSource {
    override suspend fun searchYoutubeVideos(query: String): YoutubeSearchResponseDto = service.searchVideos(query)
    override suspend fun detailContent(contentId: Int): BaseResponse<ContentDetailResponseDto> = httpClient.get("/contents/$contentId/detail").body()
    override suspend fun contentLike(contentId: Int): BaseResponse<Unit> = httpClient.post("/contents/$contentId/like").body()
    override suspend fun placeLike(placeId: Int): BaseResponse<Unit> = httpClient.post("/places/$placeId/like").body()
}