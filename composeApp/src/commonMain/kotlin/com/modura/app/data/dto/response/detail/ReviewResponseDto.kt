package com.modura.app.data.dto.response.detail

import com.modura.app.domain.model.response.detail.ReviewResponseModel

data class ReviewResponseDto(
    val id: Int,
    val username: String,
    val rating: Int,
    val comment: String,
    val createdAt: String
){
    fun toReviewResponseModel() = ReviewResponseModel(id, username, rating, comment, createdAt)
}
