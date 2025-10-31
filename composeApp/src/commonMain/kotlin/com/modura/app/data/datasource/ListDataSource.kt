package com.modura.app.data.datasource

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.list.MediaListResponseDto

interface ListDataSource {
    suspend fun getMediaList(): BaseResponse<MediaListResponseDto>
}