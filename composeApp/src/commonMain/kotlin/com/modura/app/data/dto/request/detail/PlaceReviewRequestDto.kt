package com.modura.app.data.dto.request.detail

import kotlinx.serialization.Serializable

@Serializable
data class PlaceReviewRequestDto(
    val rating: Int,
    val comment: String,
    val imageUrl: List<String?>
)
