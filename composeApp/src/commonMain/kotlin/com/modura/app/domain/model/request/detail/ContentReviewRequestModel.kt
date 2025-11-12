package com.modura.app.domain.model.request.detail

import com.modura.app.data.dto.request.detail.ContentReviewRequestDto
import kotlinx.serialization.Serializable

@Serializable
data class ContentReviewRequestModel(
    val rating: Int,
    val comment: String
){
    fun toContentReviewRequestDto() = ContentReviewRequestDto(rating, comment)
}
