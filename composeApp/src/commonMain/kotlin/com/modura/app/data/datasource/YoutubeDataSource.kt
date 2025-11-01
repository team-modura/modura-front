package com.modura.app.data.datasource

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto

interface YoutubeDataSource {
    suspend fun searchYoutubeVideos(query: String): YoutubeSearchResponseDto
}
