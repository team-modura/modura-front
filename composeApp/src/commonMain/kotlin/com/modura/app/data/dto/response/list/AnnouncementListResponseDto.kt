package com.modura.app.data.dto.response.list

import com.modura.app.domain.model.response.list.AnnouncementListResponseModel

data class AnnouncementListResponseDto(
    val list: List<AnnouncementResponseDto>
){
    fun toAnnouncementListResponseModel() = AnnouncementListResponseModel(list.map{it.toAnnouncementResponseModel()})
}