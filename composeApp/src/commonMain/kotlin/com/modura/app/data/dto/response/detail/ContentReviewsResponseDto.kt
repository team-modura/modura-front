package com.modura.app.data.dto.response.detail

import com.modura.app.domain.model.response.detail.ContentReviewResponseModel
import com.modura.app.domain.model.response.detail.ContentReviewsResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class ContentReviewsResponseDto(
    val reviews: List<ContentReviewResponseDto>

){
    fun toContentReviewsResponseModel() = ContentReviewsResponseModel(reviews.map { it.toContentReviewResponseModel() })
}
