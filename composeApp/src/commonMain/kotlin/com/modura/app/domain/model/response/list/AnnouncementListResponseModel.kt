package com.modura.app.domain.model.response.list

import com.modura.app.data.dto.response.list.AnnouncementListResponseDto
import com.modura.app.data.dto.response.list.AnnouncementResponseDto

data class AnnouncementListResponseModel(
    val list: List<AnnouncementResponseModel>
) {
    fun toAnnouncementListResponseDto() =
        AnnouncementListResponseDto(list.map { it.toAnnouncementResponseDto() })
}