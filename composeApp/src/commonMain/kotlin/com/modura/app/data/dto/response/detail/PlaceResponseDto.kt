package com.modura.app.data.dto.response.detail

import com.modura.app.domain.model.response.detail.PlaceResponseModel

data class PlaceResponseDto(
    val id: Int,
    val name: String,
    val thumbnail: String,
    val isLiked: Boolean
){
    fun toPlaceResponseModel() = PlaceResponseModel(id, name, thumbnail, isLiked)
}
