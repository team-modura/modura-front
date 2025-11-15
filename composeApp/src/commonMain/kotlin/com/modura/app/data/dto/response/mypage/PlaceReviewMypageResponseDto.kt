package com.modura.app.data.dto.response.mypage

import com.modura.app.domain.model.response.mypage.ContentReviewMypageResponseModel
import com.modura.app.domain.model.response.mypage.PlaceReviewMypageResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class PlaceReviewMypageResponseDto(
    val id: Int,
    val placeId: Int,
    val name: String,
    val username: String,
    val rating: Int,
    val comment: String,
    val imageUrl: List<String>,
    val createdAt: String,
    val thumbnail: String?
) {
    fun toPlaceReviewMypageResponseModel() =
        PlaceReviewMypageResponseModel(id, placeId, name, username, rating, comment, imageUrl,createdAt, thumbnail)
}