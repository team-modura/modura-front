package com.modura.app.data.service

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.list.MediaListResponseDto

interface ListService {
    suspend fun getMediaList(): BaseResponse<MediaListResponseDto>
}