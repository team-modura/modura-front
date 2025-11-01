package com.modura.app.data.datasourceImpl

import com.modura.app.data.datasource.ListDataSource
import com.modura.app.data.datasource.YoutubeDataSource
import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.list.MediaListResponseDto
import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto
import com.modura.app.data.service.ListService
import com.modura.app.data.service.YoutubeService

class YoutubeDataSourceImpl(private val youtubeService: YoutubeService
) : YoutubeDataSource {
    override suspend fun searchYoutubeVideos(query: String): YoutubeSearchResponseDto =
        youtubeService.searchVideos(query)

}