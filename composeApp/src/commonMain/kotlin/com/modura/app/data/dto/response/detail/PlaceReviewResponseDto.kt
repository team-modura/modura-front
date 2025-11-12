package com.modura.app.data.dto.response.detail

import com.modura.app.domain.model.response.detail.ContentReviewResponseModel
import com.modura.app.domain.model.response.detail.PlaceReviewResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class PlaceReviewResponseDto(
    val placeReviewId: Int,
    val username: String,
    val rating: Int,
    val comment: String,
    val createdAt: String,
    val imageUrl: List<String>
){
    fun toPlaceReviewResponseModel() = PlaceReviewResponseModel(placeReviewId, username, rating, comment, createdAt, imageUrl)
}
