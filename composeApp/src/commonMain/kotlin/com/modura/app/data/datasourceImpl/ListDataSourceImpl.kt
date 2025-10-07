package com.modura.app.data.datasourceImpl

import com.modura.app.data.datasource.ListDataSource
import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.list.AnnouncementListResponseDto
import com.modura.app.data.dto.response.list.AnnouncementResponseDto
import com.modura.app.data.service.ListService

class ListDataSourceImpl(
    private val listService: ListService
) : ListDataSource {
    override suspend fun getAnnouncementDetail(id: Int): BaseResponse<AnnouncementResponseDto> = listService.getAnnouncementDetail(id)
    override suspend fun getAnnouncementList(): BaseResponse<AnnouncementListResponseDto> = listService.getAnnouncementList()
}