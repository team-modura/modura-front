package com.modura.app.domain.model.response.mypage

import com.modura.app.domain.model.response.mypage.ContentReviewMypageResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class PlaceReviewMypageResponseModel(
    val id: Int,
    val name: String,
    val username: String,
    val rating: Int,
    val comment: String,
    val imageUrl: List<String>,
    val createdAt: String,
    val thumbnail: String?
)