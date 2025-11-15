package com.modura.app.data.dto.response.mypage

import com.modura.app.domain.model.response.mypage.ContentLikedResponseModel
import com.modura.app.domain.model.response.mypage.ContentReviewsMypageResponseModel
import com.modura.app.domain.model.response.mypage.ContentsLikedResponseModel
import com.modura.app.domain.model.response.mypage.StillcutResponseModel
import com.modura.app.domain.model.response.mypage.StillcutsResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class ContentReviewsMypageResponseDto(
    val contentReviewList: List<ContentReviewMypageResponseDto>
) {
    fun toCotentReviewsMypageResponseModel() = ContentReviewsMypageResponseModel(contentReviewList.map { it.toCotentReviewMypageResponseModel() })
}