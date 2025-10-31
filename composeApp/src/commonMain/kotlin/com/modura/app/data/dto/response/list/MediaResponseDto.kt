package com.modura.app.data.dto.response.list

import com.modura.app.domain.model.response.list.MediaListResponseModel
import com.modura.app.domain.model.response.list.MediaResponseModel

data class MediaResponseDto(
    val id: Int,
    val rank: String,
    val title: String,
    val bookmark: Boolean,
    val ott: List<String>,
    val image: String
) {
    fun toMediaResponseModel() = MediaResponseModel(id, rank, title, bookmark, ott, image)
}

data class MediaListResponseDto(
    val list: List<MediaResponseDto>
){
    fun toMediaListResponseModel() = MediaListResponseModel(list.map { it.toMediaResponseModel() })
}
