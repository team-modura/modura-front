package com.modura.app.data.service

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.list.MediaListResponseDto
import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto

interface YoutubeService {
    suspend fun searchVideos(query: String): YoutubeSearchResponseDto
}