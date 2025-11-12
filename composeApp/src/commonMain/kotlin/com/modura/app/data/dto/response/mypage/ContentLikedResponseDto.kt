package com.modura.app.data.dto.response.mypage

import com.modura.app.domain.model.response.mypage.ContentLikedResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class ContentLikedResponseDto(
    val id: Int,
    val title: String,
    val isLiked: Boolean,
    val thumnail: String?
){
    fun toContentLikedResponseModel() = ContentLikedResponseModel(id, title, isLiked, thumnail)
}
