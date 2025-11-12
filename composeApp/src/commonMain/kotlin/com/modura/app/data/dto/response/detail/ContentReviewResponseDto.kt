package com.modura.app.data.dto.response.detail

import com.modura.app.domain.model.response.detail.ContentReviewResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class ContentReviewResponseDto(
    val id: Int,
    val username: String,
    val rating: Int,
    val comment: String,
    val createdAt: String
){
    fun toContentReviewResponseModel() = ContentReviewResponseModel(id, username, rating, comment, createdAt)
}
