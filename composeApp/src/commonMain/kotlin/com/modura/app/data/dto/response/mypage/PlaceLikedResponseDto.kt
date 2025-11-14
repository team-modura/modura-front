package com.modura.app.data.dto.response.mypage

import com.modura.app.domain.model.response.mypage.ContentLikedResponseModel
import com.modura.app.domain.model.response.mypage.PlaceLikedResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class PlaceLikedResponseDto(
    val id: Int,
    val name: String,
    val isLiked: Boolean,
    val thumbnail: String?
){
    fun toPlaceLikedResponseModel() = PlaceLikedResponseModel(id, name, isLiked, thumbnail)
}
