package com.modura.app.data.dto.response.detail

import com.modura.app.domain.model.response.detail.ContentReviewResponseModel
import com.modura.app.domain.model.response.detail.PlaceReviewResponseModel
import com.modura.app.domain.model.response.detail.ReviewofPlaceResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class ReviewofPlaceResponseDto(
    val placeReviewId: Int,
    val username: String,
    val rating: Int,
    val comment: String,
    val imageUrl: List<String>,
    val createdAt: String
){
    fun toReviewofPlaceResponseModel() = ReviewofPlaceResponseModel(placeReviewId, username, rating, comment, imageUrl, createdAt)
}
