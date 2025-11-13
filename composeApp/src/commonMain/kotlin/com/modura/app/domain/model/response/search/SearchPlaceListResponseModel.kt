package com.modura.app.domain.model.response.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchPlaceListResponseModel(
    val placeList : List<SearchPlaceResponseModel> = emptyList()
)