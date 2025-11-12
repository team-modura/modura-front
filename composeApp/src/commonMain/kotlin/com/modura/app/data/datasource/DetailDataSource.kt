package com.modura.app.data.datasource

import com.modura.app.data.dto.response.detail.DetailResponseDto
import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto

interface DetailDataSource {
    suspend fun searchYoutubeVideos(query: String): YoutubeSearchResponseDto
    suspend fun detailContent(contentId: Int): DetailResponseDto
}
