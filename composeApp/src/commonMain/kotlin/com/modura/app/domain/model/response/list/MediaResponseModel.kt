package com.modura.app.domain.model.response.list

import com.modura.app.data.dto.response.list.MediaListResponseDto
import com.modura.app.data.dto.response.list.MediaResponseDto

data class MediaResponseModel(
    val id: Int,
    val rank: String,
    val title: String,
    val bookmark: Boolean,
    val ott: List<String>,
    val image: String
) {
    fun toMediaResponseDto() = MediaResponseDto(id, rank, title, bookmark, ott, image)
}

data class MediaListResponseModel(
    val list: List<MediaResponseModel>
){
    fun toMediaListResponseDto() = MediaListResponseDto(list.map { it.toMediaResponseDto() })
}
