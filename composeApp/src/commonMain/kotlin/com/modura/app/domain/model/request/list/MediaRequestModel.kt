package com.modura.app.domain.model.request.list

import com.modura.app.data.dto.request.list.MediaRequestDto
import kotlinx.serialization.Serializable

@Serializable
data class MediaRequestModel (
    val id: Int
){
    fun toMediaRequestDto() =
        MediaRequestDto(id)
}