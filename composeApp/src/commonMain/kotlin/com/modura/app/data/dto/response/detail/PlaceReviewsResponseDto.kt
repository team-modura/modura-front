package com.modura.app.data.dto.response.detail

import com.modura.app.domain.model.response.detail.ContentReviewResponseModel
import com.modura.app.domain.model.response.detail.ContentReviewsResponseModel
import com.modura.app.domain.model.response.detail.PlaceReviewsResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class PlaceReviewsResponseDto(
    val reviews: List<PlaceReviewResponseDto>

){
    fun toPlaceReviewsResponseModel() = PlaceReviewsResponseModel(reviews.map { it.toPlaceReviewResponseModel() })
}
