package com.modura.app.domain.model.request.list

import com.modura.app.data.dto.request.list.AnnouncementRequestDto
import kotlinx.serialization.Serializable

@Serializable
data class AnnouncementRequestModel (
    val id: Int
){
    fun toAnnouncementRequestDto() =
        AnnouncementRequestDto(id)
}