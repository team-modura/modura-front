package com.modura.app.data.datasource

import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.list.AnnouncementListResponseDto
import com.modura.app.data.dto.response.list.AnnouncementResponseDto
import com.modura.app.domain.model.response.list.AnnouncementResponseModel

interface ListDataSource {
    suspend fun getAnnouncementList(): BaseResponse<AnnouncementListResponseDto>
    suspend fun getAnnouncementDetail(id: Int): BaseResponse<AnnouncementResponseDto>
}