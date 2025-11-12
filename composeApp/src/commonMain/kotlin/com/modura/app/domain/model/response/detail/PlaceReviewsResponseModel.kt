package com.modura.app.domain.model.response.detail

import kotlinx.serialization.Serializable

@Serializable
data class PlaceReviewsResponseModel(
    val reviews: List<PlaceReviewResponseModel>
)
