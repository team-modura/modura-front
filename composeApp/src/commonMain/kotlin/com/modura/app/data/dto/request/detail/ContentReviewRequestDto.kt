package com.modura.app.data.dto.request.detail

import kotlinx.serialization.Serializable

@Serializable
data class ContentReviewRequestDto(
    val rating: Int,
    val comment: String
)
