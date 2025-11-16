package com.modura.app.data.dto.response.mypage

import com.modura.app.domain.model.response.mypage.ContentLikedResponseModel
import com.modura.app.domain.model.response.mypage.ContentReviewMypageResponseModel
import com.modura.app.domain.model.response.mypage.ContentsLikedResponseModel
import com.modura.app.domain.model.response.mypage.StillcutResponseModel
import com.modura.app.domain.model.response.mypage.StillcutsResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class ContentReviewMypageResponseDto(
    val id: Int,
    val contentId: Int,
    val title: String,
    val username: String,
    val rating: Int,
    val comment: String,
    val createdAt: String,
    val thumbnail: String?
) {
    fun toCotentReviewMypageResponseModel() = ContentReviewMypageResponseModel(id, contentId, title, username, rating, comment, createdAt, thumbnail)
}