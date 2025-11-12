package com.modura.app.data.datasource

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.detail.ContentDetailResponseDto
import com.modura.app.data.dto.response.youtube.YoutubeSearchResponseDto

interface DetailDataSource {
    suspend fun searchYoutubeVideos(query: String): YoutubeSearchResponseDto

    suspend fun detailContent(contentId: Int): BaseResponse<ContentDetailResponseDto>
    suspend fun contentLike(contentId: Int): BaseResponse<Unit>
    suspend fun placeLike(placeId: Int): BaseResponse<Unit>
}
