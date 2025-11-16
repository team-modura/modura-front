package com.modura.app.domain.model.response.map

import kotlinx.serialization.Serializable

@Serializable
data class PlaceResponseModel(
    val id: Int,
    val name: String,
    val isLiked: Boolean,
    val thumbnail: String?,
    val rating: Double,
    val reviewCount: Int,
    val latitude: Double,
    val longitude: Double,
    val content: List<String>
)
