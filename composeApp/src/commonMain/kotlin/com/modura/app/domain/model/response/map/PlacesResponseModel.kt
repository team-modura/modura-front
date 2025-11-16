package com.modura.app.domain.model.response.map

import kotlinx.serialization.Serializable

@Serializable
data class PlacesResponseModel(
    val placeList: List<PlaceResponseModel>
)