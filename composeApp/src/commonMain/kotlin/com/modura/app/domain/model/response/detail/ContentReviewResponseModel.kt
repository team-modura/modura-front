package com.modura.app.domain.model.response.detail

import kotlinx.serialization.Serializable

@Serializable
data class ContentReviewResponseModel(
    val id: Int,
    val username: String,
    val rating: Int,
    val comment: String,
    val createdAt: String
)
