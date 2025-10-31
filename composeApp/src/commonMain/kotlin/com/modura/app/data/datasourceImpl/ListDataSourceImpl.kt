package com.modura.app.data.datasourceImpl

import com.modura.app.data.datasource.ListDataSource
import com.modura.app.data.dto.BaseResponse
import com.modura.app.data.dto.response.list.MediaListResponseDto
import com.modura.app.data.service.ListService

class ListDataSourceImpl(
    private val listService: ListService
) : ListDataSource {
    override suspend fun getMediaList(): BaseResponse<MediaListResponseDto> = listService.getMediaList()
}