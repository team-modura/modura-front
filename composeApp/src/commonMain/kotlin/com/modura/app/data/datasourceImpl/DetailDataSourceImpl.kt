package com.modura.app.data.datasourceImpl

import com.modura.app.data.datasource.DetailDataSource
import com.modura.app.data.dto.response.detail.DetailResponseDto
import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto
import com.modura.app.data.service.YoutubeService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DetailDataSourceImpl(
    private val service: YoutubeService,
    private val httpClient: HttpClient
) : DetailDataSource {
    override suspend fun searchYoutubeVideos(query: String): YoutubeSearchResponseDto = service.searchVideos(query)
    override suspend fun detailContent(contentId: Int): DetailResponseDto = httpClient.get("/contents/$contentId/detail").body()

}