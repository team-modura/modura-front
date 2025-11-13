package com.modura.app.data.dto.response.mypage

import com.modura.app.domain.model.response.mypage.ContentLikedResponseModel
import com.modura.app.domain.model.response.mypage.ContentsLikedResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class ContentsLikedResponseDto(
    val contentList: List<ContentLikedResponseDto> = emptyList()
){
    fun toContentsLikedResponseModel() = ContentsLikedResponseModel(contentList.map { it.toContentLikedResponseModel() })
}