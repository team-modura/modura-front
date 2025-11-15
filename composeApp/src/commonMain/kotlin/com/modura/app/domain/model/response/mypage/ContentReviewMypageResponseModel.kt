package com.modura.app.domain.model.response.mypage

import com.modura.app.domain.model.response.mypage.ContentLikedResponseModel
import com.modura.app.domain.model.response.mypage.ContentsLikedResponseModel
import com.modura.app.domain.model.response.mypage.StillcutResponseModel
import com.modura.app.domain.model.response.mypage.StillcutsResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class ContentReviewMypageResponseModel(
    val id: Int,
    val title: String,
    val username: String,
    val rating: Int,
    val comment: String,
    val createdAt: String,
    val thumbnail: String?
)