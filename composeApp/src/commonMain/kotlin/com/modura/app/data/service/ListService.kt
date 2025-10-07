package com.modura.app.data.service

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.list.AnnouncementListResponseDto
import com.modura.app.data.dto.response.list.AnnouncementResponseDto

interface ListService {
    suspend fun getAnnouncementList(): BaseResponse<AnnouncementListResponseDto>
    suspend fun getAnnouncementDetail(id: Int): BaseResponse<AnnouncementResponseDto>
}