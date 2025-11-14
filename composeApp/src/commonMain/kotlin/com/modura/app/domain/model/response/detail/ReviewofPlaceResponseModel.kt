package com.modura.app.domain.model.response.detail

import com.modura.app.domain.model.response.detail.ContentReviewResponseModel
import com.modura.app.domain.model.response.detail.PlaceReviewResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class ReviewofPlaceResponseModel(
    val placeReviewId: Int,
    val username: String,
    val rating: Int,
    val comment: String,
    val imageUrl: List<String>,
    val createdAt: String
)